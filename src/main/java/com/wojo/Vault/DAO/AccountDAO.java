package com.wojo.Vault.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import com.wojo.Vault.Model.Account;
import com.wojo.Vault.Util.DBUtil;

public class AccountDAO {

	public static String generateLogin() {
		Random random = new Random();
		return generateRandomString(random, 9);
	}

	private static String generateRandomString(Random random, int length) {
		return random.ints(48, 90).filter(i -> (i < 58 || i > 64))
				.mapToObj(i -> (char) i).limit(length)
				.collect(StringBuilder::new, StringBuilder::append,
						StringBuilder::append)
				.toString();
	}

	public static void insertAccount(List<String> accountDate)
			throws ClassNotFoundException, SQLException {
		int nextId = getNextId();

		String updateStmt = "INSERT INTO `bankdate`.`person`\r\n"
				+ "(`idPerson`,\r\n" + "`FIRST_NAME`,\r\n" + "`LAST_NAME`,\r\n"
				+ "`PERSON_ID`,\r\n" + "`ADRESS`,\r\n"
				+ "`TELEPHON_NUMBER`,\r\n" + "`EMAIL`,\r\n" + "`LOGIN`,\r\n"
				+ "`PASSWORD`)\r\n" + "VALUES\r\n" + "('" + nextId + "',\r\n"
				+ " '" + accountDate.get(0) + "',\r\n" + " '"
				+ accountDate.get(1) + "',\r\n" + " '" + accountDate.get(2)
				+ "', \r\n" + " '" + accountDate.get(3) + "',\r\n" + " '"
				+ accountDate.get(4) + "',\r\n" + " '" + accountDate.get(5)
				+ "',\r\n" + " '" + accountDate.get(6) + "',\r\n" + " '"
				+ accountDate.get(7) + "');";
		DBUtil.dbExecuteUpdate(updateStmt);
	}

	private static int getNextId() throws ClassNotFoundException, SQLException {
		ResultSet resultSet = DBUtil
				.dbExecuteQuery("SELECT COUNT(idPerson) FROM person");
		resultSet.next();
		int result = resultSet.getInt(1);

		return result + 1;
	}

	public static boolean searchPersonLogin(String login)
			throws ClassNotFoundException, SQLException {
		String queryStatement = "SELECT COUNT(LOGIN) FROM person WHERE LOGIN = '"
				+ login + "';";
		ResultSet resulSet = DBUtil.dbExecuteQuery(queryStatement);
		resulSet.next();

		return resulSet.getInt(1) != 0;
	}

	public static void insertPersonDate(int idPerson) {
		String queryStatement = "SELECT * FROM person WHERE idPerson = '"
				+ idPerson + "'";
		ResultSet resultSet = null;
		try {
			resultSet = DBUtil.dbExecuteQuery(queryStatement);
			resultSet.next();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			Account.setFirstName(resultSet.getString(2));
			Account.setLastName(resultSet.getString(3));
			Account.setPersonId(resultSet.getString(4));
			Account.setAdress(resultSet.getString(5));
			Account.setTelephonNumber(resultSet.getString(6));
			Account.setEmail(resultSet.getString(7));
			Account.setLogin(resultSet.getString(8));
			Account.setPassword(resultSet.getString(9));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
