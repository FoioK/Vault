package com.wojo.Vault;

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
			System.err.println("JDBC Driver Exception");
			e.printStackTrace();
			throw e;
		}

		try {
			conn = DriverManager.getConnection(connStr, "root", "sqlPassword");
		} catch (SQLException e) {
			System.err.println("Connection failed!");
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
			System.err.println("Problem occurred at executeQuery operation");
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
	
	public static void dbExecuteUpdate(String updateStatement) throws SQLException, ClassNotFoundException {
		Statement statement = null;
		try {
			dbConnect();
			statement = conn.createStatement();
			statement.executeUpdate(updateStatement);
		} catch (SQLException e) {
			System.err.println("Problem occurred at executeUpdate operation");
			throw e;
		} finally {
			if(statement != null) {
				statement.close();
			}
			dbDisconnect();
		}
	}
}
