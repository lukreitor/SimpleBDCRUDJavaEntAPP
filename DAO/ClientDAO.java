/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author auxlu
 */

import DTO.Mappers.EGender;
import Model.Client;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.JDBCUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.CallableStatement;
import java.sql.Types;
import javax.swing.JOptionPane;

public class ClientDAO {
    JDBCUtil dbUtil = new JDBCUtil();
    Connection connection = null;
    private Statement stdados = null;
    private ResultSet rsdados = null;
    private CallableStatement callableStatement = null;

    // The below code is defining a constructor for a class called `ClientDAO`.
    // Inside the constructor,
    // it is attempting to establish a connection to a database using a `dbUtil`
    // object's `getDB()`
    // method. If an `IOException` is caught during this process, it is logged as a
    // severe error.
    public ClientDAO() {
        try {
            connection = dbUtil.getDB();
        } catch (IOException ex) {
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This Java function adds a new client to a database using a stored procedure
     * and handles any
     * errors that may occur.
     * 
     * @param client an object of the Client class that contains all the information
     *               about the client
     *               to be added to the database.
     * @return A boolean value indicating whether the client was successfully added
     *         or not.
     */
    public boolean addClient(Client client) {
        String query = "{ CALL create_client(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";
        try {
            connection.setAutoCommit(false); // Start a transaction

            callableStatement = connection.prepareCall(query);

            callableStatement.setString(1, client.getCPF());
            callableStatement.setString(2, client.getUsername());
            callableStatement.setString(3, client.getFirstName());
            callableStatement.setString(4, client.getLastName());
            callableStatement.setString(5, client.getEmail());
            callableStatement.setString(6, client.getPassword());
            callableStatement.setString(7, client.getPhoneNumber());
            callableStatement.setDate(8, java.sql.Date.valueOf(client.getBirthDate()));
            callableStatement.setString(9, client.getGender().name());
            callableStatement.setString(10, client.getCity());
            callableStatement.setString(11, client.getState());
            callableStatement.setString(12, client.getCountry());
            callableStatement.setString(13, client.getOccupation());
            callableStatement.setBigDecimal(14, BigDecimal.valueOf(client.getIncome()));
            callableStatement.setString(15, client.getCardholderName());
            callableStatement.setString(16, client.getCardNumber());
            callableStatement.setString(17, client.getExpirationDate());
            callableStatement.setString(18, client.getSecurityCode());
            callableStatement.setString(19, client.getBillingAddress());

            boolean result = callableStatement.execute();
            System.out.println(result);

            if (!result) {
                int rowsAffected = callableStatement.getUpdateCount();
                System.out.println(rowsAffected + " row(s) inserted.");
            }

            connection.commit(); // Commit the transaction
            return true;
        } catch (SQLException e) {
            // Rollback the transaction in case of an error
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }

            // Handle the exception appropriately and display an error message
            if (e.getMessage().contains("duplicate key value violates unique constraint")) {
                JOptionPane.showMessageDialog(null, "The CPF already exists in the database.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error occurred while adding trainer.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        } finally {
            // Reset the auto-commit mode to its default value
            try {
                connection.setAutoCommit(true);
            } catch (SQLException autoCommitException) {
                autoCommitException.printStackTrace();
            }
        }
        return false;
    }

    /**
     * This Java function deletes a client from a database using their CPF and
     * handles potential errors
     * and transaction rollbacks.
     * 
     * @param cpf The parameter "cpf" is a String representing the CPF (Cadastro de
     *            Pessoas Físicas) of
     *            a client, which is a unique identification number used in Brazil.
     *            This method deletes the client
     *            with the given CPF from a database table named "clients".
     */
    public void deleteClient(String cpf) {
        String query = "DELETE FROM clients WHERE cpf = ?";
        try {
            connection.setAutoCommit(false); // Start a transaction

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, cpf);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println(rowsAffected + " row(s) deleted.");
                System.out.println("Deleted client CPF: " + cpf);
                JOptionPane.showMessageDialog(null, "Client deleted successfully!");
            } else {
                System.out.println("No client found with CPF: " + cpf);
                JOptionPane.showMessageDialog(null, "No client found with CPF: " + cpf, "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            boolean confirmed = JDBCUtil.showConfirmationDialog();
            System.out.println(confirmed);

            if (confirmed) {
                connection.commit();
                System.out.println("Deletion committed.");
            } else {
                connection.rollback();
                System.out.println("Deletion rolled back.");
                JOptionPane.showMessageDialog(null, "Client deletion canceled.", "Canceled",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            // Rollback the transaction in case of an error
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }

            // Handle the exception appropriately and display an error message
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while deleting client: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            // Reset the auto-commit mode to its default value
            try {
                connection.setAutoCommit(true);
            } catch (SQLException autoCommitException) {
                autoCommitException.printStackTrace();
            }
        }
    }

    /**
     * This Java function updates a client's information in a database using a
     * prepared statement.
     * 
     * @param cpfToUpdate The CPF (Brazilian individual taxpayer registry
     *                    identification) of the client
     *                    to be updated in the database.
     * @param newClient   an instance of the Client class containing the updated
     *                    information for the
     *                    client.
     */
    public void updateClient(String cpfToUpdate, Client newClient) {
        String query = "{ CALL update_client(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";
        try {
            callableStatement = connection.prepareCall(query);

            callableStatement.setString(1, cpfToUpdate);
            callableStatement.setString(2, newClient.getCPF());
            callableStatement.setString(3, newClient.getUsername());
            callableStatement.setString(4, newClient.getFirstName());
            callableStatement.setString(5, newClient.getLastName());
            callableStatement.setString(6, newClient.getEmail());
            callableStatement.setString(7, newClient.getPassword());
            callableStatement.setString(8, newClient.getPhoneNumber());
            callableStatement.setDate(9, java.sql.Date.valueOf(newClient.getBirthDate()));
            callableStatement.setString(10, newClient.getGender().name());
            callableStatement.setString(11, newClient.getCity());
            callableStatement.setString(12, newClient.getState());
            callableStatement.setString(13, newClient.getCountry());
            callableStatement.setString(14, newClient.getOccupation());
            callableStatement.setBigDecimal(15, BigDecimal.valueOf(newClient.getIncome()));
            callableStatement.setString(16, newClient.getCardholderName());
            callableStatement.setString(17, newClient.getCardNumber());
            callableStatement.setString(18, newClient.getExpirationDate());
            callableStatement.setString(19, newClient.getSecurityCode());
            callableStatement.setString(20, newClient.getBillingAddress());

            boolean hasResult = callableStatement.execute();

            if (!hasResult) {
                int rowsAffected = callableStatement.getUpdateCount();
                System.out.println(rowsAffected + " row(s) updated.");
            }
        } catch (SQLException e) {
            // Handle the exception appropriately and display an error message
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while updating client: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This Java function searches for a client by their CPF (Brazilian
     * identification number) using a
     * prepared statement and returns the client object if found, otherwise returns
     * null.
     * 
     * @param cpf The parameter "cpf" is a String representing the CPF (Cadastro de
     *            Pessoas Físicas) of
     *            a client, which is a unique identification number used in Brazil
     *            for individuals. The method
     *            "findByCPF" searches for a client in a database based on their
     *            CPF.
     * @return The method is returning a Client object that matches the given CPF
     *         (Brazilian individual
     *         taxpayer registry identification number) or null if no match is
     *         found.
     */
    public Client findByCPF(String cpf) {
        String query = "{ CALL search_client_by_cpf(?) }";
        try {
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1, cpf);
            boolean hasResult = callableStatement.execute();

            if (hasResult) {
                ResultSet resultSet = callableStatement.getResultSet();
                if (resultSet.next()) {
                    return mapResultSetToClient(resultSet);
                }
            }
        } catch (SQLException e) {
            // Handle the exception appropriately
            e.printStackTrace();
        }

        return null;
    }

    // The above code is a Java program that defines a class with three methods.
    private Client mapResultSetToClient(ResultSet resultSet) {
        Client client = new Client();

        try {
            client.setCPF(resultSet.getString("cpf"));
            client.setUsername(resultSet.getString("username"));
            client.setFirstName(resultSet.getString("first_name"));
            client.setLastName(resultSet.getString("last_name"));
            client.setEmail(resultSet.getString("email"));
            client.setPassword(resultSet.getString("password"));
            client.setPhoneNumber(resultSet.getString("phone_number"));
            client.setBirthDate(resultSet.getDate("birth_date").toLocalDate());
            client.setGender(EGender.valueOf(resultSet.getString("gender")));
            client.setCity(resultSet.getString("city"));
            client.setState(resultSet.getString("state"));
            client.setCountry(resultSet.getString("country"));
            client.setOccupation(resultSet.getString("occupation"));
            client.setIncome(resultSet.getBigDecimal("income").doubleValue());
            client.setCardholderName(resultSet.getString("card_holder_name"));
            client.setCardNumber(resultSet.getString("card_number"));
            client.setExpirationDate(resultSet.getString("expiration_date"));
            client.setSecurityCode(resultSet.getString("security_code"));
            client.setBillingAddress(resultSet.getString("billing_address"));
        } catch (SQLException e) {
            // Handle the exception appropriately or rethrow it
            e.printStackTrace();
        }

        return client;
    }
    
    public void addTrainerToClient(String clientCPF, String trainerCPF) {
    String query = "UPDATE clients SET trainer_id = ? WHERE cpf = ?";
    
    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, trainerCPF);
        statement.setString(2, clientCPF);
        
        int rowsAffected = statement.executeUpdate();
        System.out.println(rowsAffected + " row(s) updated.");
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle the exception appropriately and display an error message
        // ...
    }
}


    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();

        String query = "SELECT * FROM clients";

        try (PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                // Extract client data from the result set
                String cpf = resultSet.getString("cpf");
                String username = resultSet.getString("username");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String phoneNumber = resultSet.getString("phone_number");
                String birthDateString = resultSet.getString("birth_date");
                LocalDate birthDate = LocalDate.parse(birthDateString);

                String genderString = resultSet.getString("gender");
                EGender gender = EGender.valueOf(genderString);
                String city = resultSet.getString("city");
                String state = resultSet.getString("state");
                String country = resultSet.getString("country");
                String occupation = resultSet.getString("occupation");
                double income = resultSet.getDouble("income");
                String cardholderName = resultSet.getString("card_holder_name");
                String cardNumber = resultSet.getString("card_number");
                String expirationDate = resultSet.getString("expiration_date");
                String securityCode = resultSet.getString("security_code");
                String billingAddress = resultSet.getString("billing_address");

                // Create a new Client object
                Client client = new Client(cpf, username, firstName, lastName, email, password, phoneNumber, birthDate,
                        gender, city, state, country, occupation, income, cardholderName, cardNumber, expirationDate,
                        securityCode, billingAddress);
                // Set other client attributes...

                // Add the client to the list
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }

    public String open() {
        return "select * from clients";
    }

}
