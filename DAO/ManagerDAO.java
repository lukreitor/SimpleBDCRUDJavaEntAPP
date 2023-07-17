/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.Mappers.EGender;
import Model.Client;
import Model.Manager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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

/**
 *
 * @author auxlu
 */
public class ManagerDAO {
    JDBCUtil jdbcUtil = new JDBCUtil();
    Connection connection;
    private Statement stdados = null;
    private ResultSet rsdados = null;
    private CallableStatement callableStatement = null;

    public ManagerDAO() {
        try {
            this.connection = jdbcUtil.getDB();
        } catch (IOException ex) {
            Logger.getLogger(ManagerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This Java function adds a new manager to a database using a stored procedure
     * and handles any
     * errors that may occur.
     * 
     * @param manager an object of the Manager class that contains information about
     *                the manager to be
     *                added to the database, such as their CPF, username, first
     *                name, last name, email, password,
     *                phone number, birth date, gender, city, state, country, hire
     *                date, salary, and gym.
     * @return A boolean value indicating whether the manager was successfully added
     *         or not.
     */
    public boolean addManager(Manager manager) {
        String query = "{ CALL create_manager(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";
        try {

            connection.setAutoCommit(false); // Start a transaction

            callableStatement = connection.prepareCall(query);

            callableStatement.setString(1, manager.getCPF());
            callableStatement.setString(2, manager.getUsername());
            callableStatement.setString(3, manager.getFirstName());
            callableStatement.setString(4, manager.getLastName());
            callableStatement.setString(5, manager.getEmail());
            callableStatement.setString(6, manager.getPassword());
            callableStatement.setString(7, manager.getPhoneNumber());
            callableStatement.setDate(8, java.sql.Date.valueOf(manager.getBirthDate()));
            callableStatement.setString(9, manager.getGender().name());
            callableStatement.setString(10, manager.getCity());
            callableStatement.setString(11, manager.getState());
            callableStatement.setString(12, manager.getCountry());
            callableStatement.setDate(13, new java.sql.Date(manager.getHireDate().getTime()));
            callableStatement.setBigDecimal(14, BigDecimal.valueOf(manager.getSalary()));
            callableStatement.setString(15, manager.getGym());

            boolean result = callableStatement.execute();
            System.out.println(result);

            if (!result) {
                int rowsAffected = callableStatement.getUpdateCount();
                System.out.println(rowsAffected + " row(s) inserted.");
            }

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
                JOptionPane.showMessageDialog(null, "Error occurred while adding manager.", "Error",
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

    public void updateManager(String cpfToUpdate, Manager newManager) {
        String query = "{ CALL update_manager(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";
        try {
            callableStatement = connection.prepareCall(query);

            callableStatement.setString(1, cpfToUpdate);
            callableStatement.setString(2, newManager.getCPF());
            callableStatement.setString(3, newManager.getUsername());
            callableStatement.setString(4, newManager.getFirstName());
            callableStatement.setString(5, newManager.getLastName());
            callableStatement.setString(6, newManager.getEmail());
            callableStatement.setString(7, newManager.getPassword());
            callableStatement.setString(8, newManager.getPhoneNumber());
            callableStatement.setDate(9, java.sql.Date.valueOf(newManager.getBirthDate()));
            callableStatement.setString(10, newManager.getGender().name());
            callableStatement.setString(11, newManager.getCity());
            callableStatement.setString(12, newManager.getState());
            callableStatement.setString(13, newManager.getCountry());
            callableStatement.setDate(14, new java.sql.Date(newManager.getHireDate().getTime()));
            callableStatement.setBigDecimal(15, BigDecimal.valueOf(newManager.getSalary()));
            callableStatement.setString(16, newManager.getGym());

            boolean hasResult = callableStatement.execute();

            if (!hasResult) {
                int rowsAffected = callableStatement.getUpdateCount();
                System.out.println(rowsAffected + " row(s) updated.");
            }
        } catch (SQLException e) {
            // Handle the exception appropriately and display an error message
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while updating manager: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This Java function deletes a manager from a database and handles potential
     * errors and user
     * confirmation.
     * 
     * @param cpf The CPF (Cadastro de Pessoas Físicas) is a unique identification
     *            number assigned to
     *            Brazilian citizens and residents for tax and administrative
     *            purposes. In this context, the "cpf"
     *            parameter is a String variable that represents the CPF of the
     *            manager that will be deleted from
     *            the "managers" table
     */
    public void deleteManager(String cpf) {
        String query = "DELETE FROM managers WHERE cpf = ?";
        try {
            connection.setAutoCommit(false); // Start a transaction

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, cpf);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println(rowsAffected + " row(s) deleted.");
                System.out.println("Deleted manager CPF: " + cpf);
                JOptionPane.showMessageDialog(null, "Manager deleted successfully!");
            } else {
                System.out.println("No manager found with CPF: " + cpf);
                JOptionPane.showMessageDialog(null, "No manager found with CPF: " + cpf, "Error",
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
                JOptionPane.showMessageDialog(null, "Manager deletion canceled.", "Canceled",
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
            JOptionPane.showMessageDialog(null, "Error occurred while deleting manager: " + e.getMessage(), "Error",
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
     * This Java function searches for a manager in a database by their CPF
     * (Brazilian identification
     * number) using a prepared statement and returns a Manager object if found.
     * 
     * @param cpf The parameter "cpf" is a String representing the CPF (Cadastro de
     *            Pessoas Físicas) of
     *            a manager, which is a unique identification number used in Brazil.
     *            The method "findByCPF"
     *            searches for a manager in a database based on their CPF.
     * @return The method is returning a Manager object that matches the given CPF
     *         (Brazilian individual
     *         taxpayer registry identification number) or null if no match is
     *         found.
     */
    public Manager findByCPF(String cpf) {
        String query = "{ CALL search_manager_by_cpf(?) }";
        try {
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1, cpf);
            boolean hasResult = callableStatement.execute();

            if (hasResult) {
                ResultSet resultSet = callableStatement.getResultSet();
                if (resultSet.next()) {
                    return mapResultSetToManager(resultSet);
                }
            }
        } catch (SQLException e) {
            // Handle the exception appropriately
            e.printStackTrace();
        }

        return null;
    }

    /**
     * This function maps the result set obtained from a database query to a Manager
     * object in Java.
     * 
     * @param resultSet A ResultSet object that contains the data retrieved from a
     *                  database after
     *                  executing a SQL query.
     * @return The method is returning an instance of the Manager class.
     */
    private Manager mapResultSetToManager(ResultSet resultSet) {
        Manager manager = new Manager();

        try {
            manager.setCPF(resultSet.getString("cpf"));
            manager.setUsername(resultSet.getString("username"));
            manager.setFirstName(resultSet.getString("first_name"));
            manager.setLastName(resultSet.getString("last_name"));
            manager.setEmail(resultSet.getString("email"));
            manager.setPassword(resultSet.getString("password"));
            manager.setPhoneNumber(resultSet.getString("phone_number"));
            manager.setBirthDate(resultSet.getDate("birth_date").toLocalDate());
            manager.setGender(EGender.valueOf(resultSet.getString("gender")));
            manager.setCity(resultSet.getString("city"));
            manager.setState(resultSet.getString("state"));
            manager.setCountry(resultSet.getString("country"));
            manager.setHireDate(java.sql.Date.valueOf(resultSet.getDate("hire_date").toLocalDate()));
            manager.setSalary(resultSet.getDouble("salary"));
            manager.setGym(resultSet.getString("gym"));
        } catch (SQLException e) {
            // Handle the exception appropriately or rethrow it
            e.printStackTrace();
        }

        return manager;
    }

    /**
     * This function retrieves all managers from a database and returns them as a
     * list of Manager
     * objects.
     * 
     * @return A list of Manager objects.
     */
    public List<Manager> getAllManagers() {
        List<Manager> managers = new ArrayList<>();

        String query = "SELECT * FROM managers";

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
                String gym = resultSet.getString("gym");
                String hireDateString = resultSet.getString("hire_date");
                LocalDate hireDate = LocalDate.parse(hireDateString);
                Date hireDateSql = java.sql.Date.valueOf(hireDate);

                double salary = resultSet.getDouble("salary");

                // Create a new Client object
                Manager manager = new Manager(cpf, username, firstName, lastName, email, password, phoneNumber,
                        birthDate, gender, city, state, country, gym, hireDateSql, salary);
                // Set other client attributes...

                // Add the client to the list
                managers.add(manager);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return managers;
    }

    public String open() {
        return "select * from managers";
    }

}
