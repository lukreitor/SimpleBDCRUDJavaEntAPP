/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.sql.PreparedStatement;

/**
 *
 * @author auxlu
 */
public class JDBCUtil {
    private static Connection connection;
    private static String url, user, password;
    private static final String PROPERTIES_FILE_NAME = "configdb.properties";
    private Statement stdados = null;
    private ResultSet rsdados = null;
    String querydados;

    /**
     * This function retrieves a database connection using properties from a file
     * and the PostgreSQL
     * driver.
     * 
     * @return The method is returning a Connection object.
     */
    public Connection getDB() throws IOException {
        try {
            String currentDir = System.getProperty("user.dir");
            String propertiesFilePath = currentDir + "/src/util/" + PROPERTIES_FILE_NAME;

            Properties props = new Properties();
            FileInputStream fis = new FileInputStream(propertiesFilePath);
            try {
                props.load(fis);
            } catch (IOException ex) {
                Logger.getLogger(JDBCUtil.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                fis.close();
            }

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
            // connection.setAutoCommit(true);

        } catch (ClassNotFoundException erro) {
            System.out.println("Falha ao carregar o driver JDBC/ODBC." + erro);
            return null;
        } catch (SQLException erro) {
            System.out.println("Falha na conexao, comando sql = " + erro);
            return null;
        }
        return connection;
    }

   
    

    public void setAutoComitState() {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException autoCommitException) {
            autoCommitException.printStackTrace();
        }
    }

    public String returnCPF() {
        String cpf = JOptionPane.showInputDialog("Enter CPF:");

        return cpf;
    }

    public void Sair() {
        try {
            if (rsdados != null) {
                rsdados.close();
                stdados.close();
                connection.close();
            }
        } catch (SQLException erro) {
            System.out.println("Nao foi possivel a desconexao." + erro);
        }
    }

    /**
     * This Java function checks if a given CPF (Brazilian identification number)
     * exists in a specified
     * database table.
     * 
     * @param cpf       A String representing a CPF (Brazilian individual taxpayer
     *                  registry identification)
     *                  number to be checked for existence in a database table.
     * @param tableName The name of the database table where the CPF (Brazilian
     *                  individual taxpayer
     *                  registry identification) is being checked for existence.
     * @return The method returns a boolean value indicating whether a given CPF
     *         (Brazilian
     *         identification number) exists in a specified database table.
     */
    public boolean cpfExists(String cpf, String tableName) {
        String query = "SELECT COUNT(*) FROM " + tableName + " WHERE cpf = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, cpf);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            return count > 0;
        } catch (SQLException e) {
            // Handle the exception appropriately
            e.printStackTrace();
            return false;
        }
    }

    /**
     * The function displays a confirmation dialog box and returns a boolean value
     * based on the user's
     * response.
     * 
     * @return The method is returning a boolean value, which is determined by the
     *         user's response to
     *         the confirmation dialog. If the user clicks "Yes", the method will
     *         return true. If the user
     *         clicks "No", the method will return false.
     */
    public static boolean showConfirmationDialog() {
        int dialogResult = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to proceed?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);

        return dialogResult == JOptionPane.YES_OPTION;
    }

    /**
     * The function tests if a database connection is open and returns a boolean
     * value.
     * 
     * @return The method is returning a boolean value. It returns true if the
     *         connection object is not
     *         null and is not closed, and false otherwise.
     */
    public static boolean testConnection() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void commit(Connection connection) {
        if (connection != null) {
            try {
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void ExecutaUpdate(String jTextAreaExecuteQuery) {

        try {
            querydados = jTextAreaExecuteQuery;
            int tipo = ResultSet.TYPE_SCROLL_SENSITIVE;// (c)

            int concorrencia = ResultSet.CONCUR_UPDATABLE;

            stdados = connection.createStatement(tipo, concorrencia);
            // connection.prepareStatement(sql)
            int resposta = stdados.executeUpdate(querydados);// DML
            // boolean resposta2 = stdados.execute(querydados);//DDL
            // rsdados = stdados.executeQuery(querydados);//DQL
            System.out.println("Resposta do Update = " + resposta);

        } catch (SQLException erro) {
            System.out.println("Erro Executa Update = " + erro);
        }
    }

}
