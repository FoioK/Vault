package com.wojo.Vault.Util;

import com.sun.rowset.CachedRowSetImpl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.List;
import java.util.Properties;

public class DBUtil {

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

    public static int dbExecuteUpdated(String updateStatement, List<String> updateDate)
            throws SQLException {

        PreparedStatement statement = null;
        int idPerson = 0;
        try {
            connection = getConnection();
            statement = connection.prepareStatement(updateStatement);
            if (updateDate != null) {
                for (int i = 0; i < updateDate.size(); i++) {
                    statement.setString(i + 1, updateDate.get(i));
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

    /**
     * Gets a connection from the properties specified in the file database.properties.
     *
     * @return the database connection
     */
    public static Connection getConnection() throws SQLException, IOException {
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

    public static void dbDisconnect() throws SQLException {
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