package com.wojo.Vault.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sun.rowset.CachedRowSetImpl;

public class DBUtil {

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    private static Connection conn = null;

    /**
     * Connection String: Username=root Password=sqlPassword
     */
    private static final String connStr = "jdbc:mysql://localhost:3306/BankDate?useSSL=false";

    public static void dbConnect() throws SQLException, ClassNotFoundException {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        }

        try {
            conn = DriverManager.getConnection(connStr, "root", "sqlPassword");
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void dbDisconnect() throws SQLException {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @SuppressWarnings("restriction")
    public static ResultSet dbExecuteQuery(String queryStatement)
            throws SQLException, ClassNotFoundException {
        Statement statement = null;
        ResultSet resultSet = null;
        CachedRowSetImpl crs = null;

        try {
            dbConnect();
            statement = conn.createStatement();
            resultSet = statement.executeQuery(queryStatement);

            crs = new CachedRowSetImpl();
            crs.populate(resultSet);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            dbDisconnect();
        }
        return crs;
    }

    public static int dbExecuteUpdate(String updateStatement)
            throws SQLException, ClassNotFoundException {
        Statement statement = null;
        int idPerson;
        try {
            dbConnect();
            statement = conn.createStatement();
            idPerson = statement.executeUpdate(updateStatement);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (statement != null) {
                statement.close();
            }
            dbDisconnect();
        }
        return idPerson;
    }

//    @SuppressWarnings("unused")
//    private static void dbClear() throws ClassNotFoundException, SQLException {
//        dbExecuteUpdate("TRUNCATE TABLE person;");
//    }
}
