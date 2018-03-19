package com.wojo.Vault.Database;

import com.sun.rowset.CachedRowSetImpl;
import com.wojo.Vault.Exception.DatabaseConnectionException;
import com.wojo.Vault.Exception.LoadPropertiesException;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class DBManager {

    private static Connection connection = null;

    private static final String ORIGINAL_CONNECTION_PATH = "META-INF/database.properties";
    private static final String TEST_CONNECTION_PATH = "META-INF/databaseTest.properties";
    private static String connectionPath = ORIGINAL_CONNECTION_PATH;

    public static ResultSet dbExecuteQuery(String queryStatement, List<String> queryDate)
            throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        CachedRowSetImpl cachedRowSet;

        try {
            if (connection == null || connection.isClosed()) {
                dbConnection();
                if (connection == null || connection.isClosed()) {
                    return new CachedRowSetImpl();
                }
            }
            statement = connection.prepareStatement(queryStatement);
            if (queryDate != null) {
                for (int i = 0; i < queryDate.size(); i++) {
                    statement.setString(i + 1, queryDate.get(i));
                }
            }
            resultSet = statement.executeQuery();
            cachedRowSet = new CachedRowSetImpl();
            cachedRowSet.populate(resultSet);
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }

        return cachedRowSet;
    }

    public static int dbExecuteUpdate(String updateStatement, List<String> updateData)
            throws SQLException {

        PreparedStatement statement = null;
        int updateRows;

        try {
            if (connection == null || connection.isClosed()) {
                dbConnection();
                if (connection == null || connection.isClosed()) {
                    return -1;
                }

            }
            statement = connection.prepareStatement(updateStatement);
            if (updateData != null) {
                for (int i = 0; i < updateData.size(); i++) {
                    statement.setString(i + 1, updateData.get(i));
                }
            }
            updateRows = statement.executeUpdate();
        } finally {
            if (statement != null) {
                statement.close();
            }
        }

        return updateRows;
    }

    public static boolean dbExecuteTransactionUpdate(Map<List<Object>, String> dataToUpdate)
            throws SQLException {
        if (connection == null || connection.isClosed()) {
            dbConnection();
            if (connection == null || connection.isClosed()) {
                return false;
            }
        }

        connection.setAutoCommit(false);
        List<PreparedStatement> preparedStatements = new ArrayList<>();

        try {
            dataToUpdate.entrySet()
                    .forEach(data -> {
                        PreparedStatement statement = executeStatement(data);
                        preparedStatements.add(statement);
                        if (statement == null) {
                            data.setValue(null);
                        }
                    });
        } finally {
            if (dataToUpdate.entrySet()
                    .stream()
                    .filter(data -> data.getValue() != null)
                    .count() == dataToUpdate.size()) {
                connection.commit();
            }
            preparedStatements.forEach(statement -> {
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (SQLException e) {
                        System.out.println("Close statement error");
                    }
                }
            });
        }

        connection.setAutoCommit(true);
        return true;
    }

    private static PreparedStatement executeStatement(Map.Entry<List<Object>, String> data) {
        String updateStatement = data.getValue();
        List<Object> updateData = data.getKey();
        PreparedStatement statement;

        try {
            statement = connection.prepareStatement(updateStatement);
            if (updateData != null) {
                for (int i = 0; i < updateData.size(); i++) {
                    statement.setObject(i + 1, updateData.get(i));
                }
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Execute statement error");
            return null;
        }

        return statement;
    }

    public static void dbConnection() {
        try {
            connection = getConnection();
        } catch (DatabaseConnectionException | LoadPropertiesException e) {
            tryAgain();
        }
    }

    private static void tryAgain() {
        int n = JOptionPane.showConfirmDialog(
                null,
                "Try again",
                "Database connection error",
                JOptionPane.YES_NO_OPTION);

        if (n == 0) {
            dbConnection();
        }
    }

    private static Connection getConnection() throws DatabaseConnectionException, LoadPropertiesException {
        Properties properties = new Properties();
        DBManager dbManager = new DBManager();

        try (InputStream in = dbManager.getClass().getClassLoader().getResourceAsStream(connectionPath)) {
            properties.load(in);
        } catch (IOException e) {
            throw new LoadPropertiesException("Database properties load error");
        }

        String drivers = properties.getProperty("jdbc.drivers");
        if (drivers != null) {
            System.setProperty("jdbc.drivers", drivers);
        }

        String url = properties.getProperty("jdbc.url");
        String username = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");

        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Database connection error");
        }
    }

    public static void dbDisconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Connection close failed");
                } finally {
                    if (connection != null) {
                        connection.close();
                    }
                }
            }
        } catch (SQLException ignored) {
        }
    }

    public static void setOriginalConnectionPath() {
        connectionPath = ORIGINAL_CONNECTION_PATH;
    }

    public static void setTestConnectionPath() {
        connectionPath = TEST_CONNECTION_PATH;
    }
}