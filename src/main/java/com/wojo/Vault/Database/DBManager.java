package com.wojo.Vault.Database;

import com.sun.rowset.CachedRowSetImpl;
import com.wojo.Vault.Exception.ConnectionException;
import com.wojo.Vault.Exception.ErrorCode;
import com.wojo.Vault.Exception.ExecuteStatementException;
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
            throws ExecuteStatementException {
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
        } catch (SQLException e) {
            throw new ExecuteStatementException("Execute query error", ErrorCode.EXECUTE_QUERY_ERROR);
        } finally {
            try {
                assert connection != null;
                connection.commit();
            } catch (SQLException e) {
                //noinspection ThrowFromFinallyBlock
                throw new ExecuteStatementException("Execute commit error", ErrorCode.EXECUTE_COMMIT_ERROR);
            }

            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException ignored) {
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ignored) {
                }
            }
        }

        return cachedRowSet;
    }

    public static int dbExecuteUpdate(String updateStatement, List<String> updateData)
            throws ExecuteStatementException {

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
        } catch (SQLException e) {
            throw new ExecuteStatementException("Execute update error", ErrorCode.EXECUTE_UPDATE_ERROR);
        } finally {
            try {
                assert connection != null;
                connection.commit();
            } catch (SQLException e) {
//                noinspection ThrowFromFinallyBlock
                throw new ExecuteStatementException("Execute commit error", ErrorCode.EXECUTE_COMMIT_ERROR);
            }

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ignored) {
                }
            }
        }

        return updateRows;
    }

    public static <T> boolean dbExecuteTransactionUpdate(Map<List<T>, String> dataToUpdate)
            throws ExecuteStatementException {
        try {
            if (connection == null || connection.isClosed()) {
                dbConnection();
                if (connection == null || connection.isClosed()) {
                    return false;
                }
            }
        } catch (SQLException e) {
            return false;
        }

        List<PreparedStatement> preparedStatements = new ArrayList<>(dataToUpdate.size());

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
                try {
                    connection.commit();
                } catch (SQLException e) {
//                    noinspection ThrowFromFinallyBlock
                    throw new ExecuteStatementException("Execute commit error", ErrorCode.EXECUTE_COMMIT_ERROR);
                }
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

        return true;
    }

    private static <T> PreparedStatement executeStatement(Map.Entry<List<T>, String> data) {
        String updateStatement = data.getValue();
        List<T> updateData = data.getKey();
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

    public static boolean dbConnection() {
        try {
            connection = getConnection();
            if (connection == null) {
                tryAgain();
            }
            connection.setAutoCommit(false);
        } catch (LoadPropertiesException | SQLException e) {
            tryAgain();
        }

        return true;
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

    private static Connection getConnection() throws ConnectionException, LoadPropertiesException {
        Properties properties = new Properties();
        DBManager dbManager = new DBManager();

        try (InputStream in = dbManager.getClass().getClassLoader().getResourceAsStream(connectionPath)) {
            properties.load(in);
        } catch (IOException e) {
            throw new LoadPropertiesException("Database properties load error",
                    ErrorCode.PROPERTIES_LOAD_ERROR);
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
            throw new ConnectionException("Database connection error", ErrorCode.CONNECTION_ERROR);
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