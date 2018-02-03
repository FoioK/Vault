package com.wojo.Vault.Database;

import com.sun.rowset.CachedRowSetImpl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class DBManager {

    private static Connection connection = null;

    public static ResultSet dbExecuteQuery(String queryStatement, List<String> queryDate)
            throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        CachedRowSetImpl cachedRowSet = null;
        try {
            connection = getConnection();
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
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            dbDisconnect();
        }
        return cachedRowSet;
    }

    public static int dbExecuteUpdate(String updateStatement, List<String> updateData)
            throws SQLException {

        PreparedStatement statement = null;
        int idPerson = 0;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(updateStatement);
            if (updateData != null) {
                for (int i = 0; i < updateData.size(); i++) {
                    statement.setString(i + 1, updateData.get(i));
                }
            }
            idPerson = statement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }
            dbDisconnect();
        }
        return idPerson;
    }

    public static boolean dbExecuteTransactionUpdate(Map<List<Object>, String> dataToUpdate)
            throws SQLException {
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<PreparedStatement> preparedStatements = new ArrayList<>();
        try {
            dataToUpdate.entrySet()
                    .stream()
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
                        e.printStackTrace();
                    }
                }
            });
            dbDisconnect();
        }
        return true;
    }

    private static PreparedStatement executeStatement(Map.Entry data) {
        String updateStatement = (String) data.getValue();
        List<Object> updateData = (List<Object>) data.getKey();
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
            e.printStackTrace();
            return null;
        }
        return statement;
    }

    /**
     * Gets a connection from the properties specified in the file database.properties.
     *
     * @return the database connection
     */
    private static Connection getConnection() throws SQLException, IOException {
        Properties properties = new Properties();
        String connectionPath = "src/main/resources/Database/database.properties";
        try (InputStream in = Files.newInputStream(Paths.get(connectionPath))) {
            properties.load(in);
        }
        String drivers = properties.getProperty("jdbc.drivers");
        if (drivers != null) {
            System.setProperty("jdbc.drivers", drivers);
        }
        String url = properties.getProperty("jdbc.url");
        String username = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");

        return DriverManager.getConnection(url, username, password);
    }

    private static void dbDisconnect() throws SQLException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            throw e;
        }
    }

//    @SuppressWarnings("unused")
//    private static void dbClear() throws ClassNotFoundException, SQLException {
//        dbExecuteUpdate("TRUNCATE TABLE person;");
//    }
}