package DAO;

import DTO.Mappers.EGender;
import Model.Trainer;

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
import java.util.Date;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author auxlu
 */
public class TrainerDAO {
    JDBCUtil dbUtil = new JDBCUtil();
    Connection connection = null;
    private Statement stdados = null;
    private ResultSet rsdados = null;
    private CallableStatement callableStatement = null;

    public TrainerDAO() {
        try {
            connection = dbUtil.getDB();
        } catch (IOException ex) {
            Logger.getLogger(TrainerDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This Java function adds a new trainer to a database using a stored procedure
     * and handles any
     * errors that may occur.
     * 
     * @param trainer an object of the Trainer class that contains all the
     *                information about the
     *                trainer to be added to the database.
     * @return A boolean value indicating whether the trainer was successfully added
     *         or not.
     */
    public boolean addTrainer(Trainer trainer) {
        String query = "{ CALL create_trainer(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";
        try {
            connection.setAutoCommit(false); // Start a transaction

            callableStatement = connection.prepareCall(query);

            callableStatement.setString(1, trainer.getCPF());
            callableStatement.setString(2, trainer.getUsername());
            callableStatement.setString(3, trainer.getFirstName());
            callableStatement.setString(4, trainer.getLastName());
            callableStatement.setString(5, trainer.getEmail());
            callableStatement.setString(6, trainer.getPassword());
            callableStatement.setString(7, trainer.getPhoneNumber());
            callableStatement.setDate(8, java.sql.Date.valueOf(trainer.getBirthDate()));
            callableStatement.setString(9, trainer.getGender().name());
            callableStatement.setString(10, trainer.getCity());
            callableStatement.setString(11, trainer.getState());
            callableStatement.setString(12, trainer.getCountry());
            callableStatement.setString(13, trainer.getCertification());
            callableStatement.setString(14, trainer.getSpeciality());
            callableStatement.setString(15, trainer.getGym());
            callableStatement.setBigDecimal(16, BigDecimal.valueOf(trainer.getHourlyRate()));
            callableStatement.setInt(17, trainer.getYearsOfExperience());
            callableStatement.setDate(18, new java.sql.Date(trainer.getHireDate().getTime()));
            callableStatement.setBigDecimal(19, BigDecimal.valueOf(trainer.getSalary()));

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
     * This Java function deletes a trainer from a database and handles transaction
     * management and
     * error handling.
     * 
     * @param cpf The parameter "cpf" is a String representing the CPF (Cadastro de
     *            Pessoas Físicas) of
     *            the trainer to be deleted from the database. CPF is a unique
     *            identification number assigned to
     *            Brazilian citizens and residents.
     */
    public void deleteTrainer(String cpf) {
        String query = "DELETE FROM trainers WHERE cpf = ?";
        try {
            connection.setAutoCommit(false); // Start a transaction

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, cpf);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println(rowsAffected + " row(s) deleted.");
                System.out.println("Deleted trainer CPF: " + cpf);
                JOptionPane.showMessageDialog(null, "Trainer deleted successfully!");
            } else {
                System.out.println("No trainer found with CPF: " + cpf);
                JOptionPane.showMessageDialog(null, "No trainer found with CPF: " + cpf, "Error",
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
                JOptionPane.showMessageDialog(null, "Trainer deletion canceled.", "Canceled",
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
            JOptionPane.showMessageDialog(null, "Error occurred while deleting trainer: " + e.getMessage(), "Error",
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
     * This Java function updates a trainer's information in a database using a
     * prepared statement.
     * 
     * @param cpfToUpdate The CPF (Brazilian individual taxpayer registry
     *                    identification) of the
     *                    trainer to be updated in the database.
     * @param newTrainer  an object of the Trainer class containing the updated
     *                    information for the
     *                    trainer.
     */
    public void updateTrainer(String cpfToUpdate, Trainer newTrainer) {
        String query = "{ CALL update_trainer(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";
        try {
            callableStatement = connection.prepareCall(query);

            callableStatement.setString(1, cpfToUpdate);
            callableStatement.setString(2, newTrainer.getCPF());
            callableStatement.setString(3, newTrainer.getUsername());
            callableStatement.setString(4, newTrainer.getFirstName());
            callableStatement.setString(5, newTrainer.getLastName());
            callableStatement.setString(6, newTrainer.getEmail());
            callableStatement.setString(7, newTrainer.getPassword());
            callableStatement.setString(8, newTrainer.getPhoneNumber());
            callableStatement.setDate(9, java.sql.Date.valueOf(newTrainer.getBirthDate()));
            callableStatement.setString(10, newTrainer.getGender().name());
            callableStatement.setString(11, newTrainer.getCity());
            callableStatement.setString(12, newTrainer.getState());
            callableStatement.setString(13, newTrainer.getCountry());
            callableStatement.setString(14, newTrainer.getCertification());
            callableStatement.setString(15, newTrainer.getSpeciality());
            callableStatement.setString(16, newTrainer.getGym());
            callableStatement.setBigDecimal(17, BigDecimal.valueOf(newTrainer.getHourlyRate()));
            callableStatement.setInt(18, newTrainer.getYearsOfExperience());
            callableStatement.setDate(19, new java.sql.Date(newTrainer.getHireDate().getTime()));
            callableStatement.setBigDecimal(20, BigDecimal.valueOf(newTrainer.getSalary()));

            boolean hasResult = callableStatement.execute();

            if (!hasResult) {
                int rowsAffected = callableStatement.getUpdateCount();
                System.out.println(rowsAffected + " row(s) updated.");
            }
        } catch (SQLException e) {
            // Handle the exception appropriately and display an error message
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error occurred while updating trainer: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This Java function searches for a trainer in a database by their CPF
     * (Brazilian identification
     * number).
     * 
     * @param cpf A String representing the CPF (Cadastro de Pessoas Físicas) of a
     *            trainer, which is a
     *            unique identification number used in Brazil.
     * @return The method is returning a Trainer object that matches the given CPF
     *         (Brazilian individual
     *         taxpayer registry identification number) or null if no match is
     *         found.
     */
    public Trainer findByCPF(String cpf) {
        String query = "{ CALL search_trainer_by_cpf(?) }";
        try {
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1, cpf);
            boolean hasResult = callableStatement.execute();

            if (hasResult) {
                ResultSet resultSet = callableStatement.getResultSet();
                if (resultSet.next()) {
                    return mapResultSetToTrainer(resultSet);
                }
            }
        } catch (SQLException e) {
            // Handle the exception appropriately
            e.printStackTrace();
        }

        return null;
    }

    /**
     * This function maps the result set of a SQL query to a Trainer object in Java.
     * 
     * @param resultSet A ResultSet object that contains the data retrieved from a
     *                  database after
     *                  executing a SQL query.
     * @return The method is returning an instance of the Trainer class.
     */
    private Trainer mapResultSetToTrainer(ResultSet resultSet) {
        Trainer trainer = new Trainer();

        try {
            trainer.setCPF(resultSet.getString("cpf"));
            trainer.setUsername(resultSet.getString("username"));
            trainer.setFirstName(resultSet.getString("first_name"));
            trainer.setLastName(resultSet.getString("last_name"));
            trainer.setEmail(resultSet.getString("email"));
            trainer.setPassword(resultSet.getString("password"));
            trainer.setPhoneNumber(resultSet.getString("phone_number"));
            trainer.setBirthDate(resultSet.getDate("birth_date").toLocalDate());
            trainer.setGender(EGender.valueOf(resultSet.getString("gender")));
            trainer.setCity(resultSet.getString("city"));
            trainer.setState(resultSet.getString("state"));
            trainer.setCountry(resultSet.getString("country"));
            trainer.setCertification(resultSet.getString("certification"));
            trainer.setSpeciality(resultSet.getString("speciality"));
            trainer.setGym(resultSet.getString("gym"));
            trainer.setHourlyRate(resultSet.getDouble("hourly_rate"));
            trainer.setYearsOfExperience(resultSet.getInt("years_of_experience"));
            trainer.setHireDate(resultSet.getDate("hire_date"));
            trainer.setSalary(resultSet.getDouble("salary"));

        } catch (SQLException e) {
            // Handle the exception appropriately or rethrow it
            e.printStackTrace();
        }

        return trainer;
    }

    /**
     * This function retrieves all trainers from a database and returns them as a
     * list of Trainer
     * objects.
     * 
     * @return A list of Trainer objects.
     */
    public List<Trainer> getAllTrainers() {
        List<Trainer> trainers = new ArrayList<>();

        String query = "SELECT * FROM trainers";

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
                String certification = resultSet.getString("certification");
                String speciality = resultSet.getString("speciality");
                String gym = resultSet.getString("gym");
                double hourlyRate = resultSet.getDouble("hourly_rate");
                int yearsOfExperience = resultSet.getInt("years_of_experience");
                // Date hireDate = resultSet.getDate("hire_date");
                // double salary = resultSet.getDouble("salary");

                // Create a new Client object
                Trainer trainer = new Trainer(cpf, username, firstName, lastName, email, password, phoneNumber,
                        birthDate, gender, city, state, country, certification, speciality, gym, hourlyRate,
                        yearsOfExperience);
                // Set other client attributes...

                // Add the client to the list
                trainers.add(trainer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return trainers;
    }

    /**
     * The function returns a SQL query to select all trainers.
     * 
     * @return The method `open()` is returning a `String` that contains a SQL query
     *         to select all
     *         columns from the `trainers` table.
     */
    public String open() {
        return "select * from trainers";
    }
}
