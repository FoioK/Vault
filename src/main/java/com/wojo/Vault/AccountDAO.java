package com.wojo.Vault;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

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
	
	public static void insertAccount(List<String> accountDate) throws ClassNotFoundException, SQLException {
//        int nextId = getNextId();
		
		String updateStmt = "INSERT INTO `bankdate`.`person`\r\n" + 
				"(`idPerson`,\r\n" + 
				"`FIRST_NAME`,\r\n" + 
				"`LAST_NAME`,\r\n" + 
				"`PERSON_ID`,\r\n" + 
				"`ADRESS`,\r\n" + 
				"`TELEPHON_NUMBER`,\r\n" + 
				"`EMAIL`,\r\n" + 
				"`LOGIN`,\r\n" + 
				"`PASSWORD`)\r\n" + 
				"VALUES\r\n" + 
				"('1',\r\n" + 
				" '"+accountDate.get(0)+"',\r\n" + 
				" '"+accountDate.get(1)+"',\r\n" + 
				" '"+accountDate.get(2)+"', \r\n" +
				" '"+accountDate.get(3)+"',\r\n" + 
				" '"+accountDate.get(4)+"',\r\n" + 
				" '"+accountDate.get(5)+"',\r\n" + 
				" '"+accountDate.get(6)+"',\r\n" + 
				" '"+accountDate.get(7)+"');";
        DBUtil.dbExecuteUpdate(updateStmt);
	}

//	private static int getNextId() throws ClassNotFoundException, SQLException {
//		int result = DBUtil
//				.dbExecuteQuery("SELECT COUNT(idPerson) FROM person")
//				.getInt(1);
//
//		return result + 1;
//	}
}
