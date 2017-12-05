package com.wojo.Vault;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

public class DBUtilTest {

	@Test
	public void connectionTest() {
		try {
			DBUtil.dbConnect();
		} catch (ClassNotFoundException e) {
			fail("ClassNotFoundException");
		} catch (SQLException e) {
			fail("SQLException");
		}
	}

	@Test
	public void disconnectionTest() {
		try {
			DBUtil.dbDisconnect();
		} catch (SQLException e) {
			fail("SQL Exception");
		}
	}

	@Test
	public void executeQueryTest() throws SQLException, ClassNotFoundException {
		DBUtil.dbExecuteQuery("SELECT * FROM person");
	}
	
	@Test
	public void updateQueryTest() throws ClassNotFoundException, SQLException {
		DBUtil.dbExecuteUpdate("INSERT INTO `bankdate`.`person`\r\n" + 
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
				"('100',\r\n" + 
				" 'TestFirstName',\r\n" + 
				" 'TestLastName',\r\n" + 
				" '12345678910', 'TestStreet 37',\r\n" + 
				" '500600700',\r\n" + 
				" 'testMail@test.pl',\r\n" + 
				" 'testLogin',\r\n" + 
				" 'testPassword');");
		DBUtil.dbExecuteUpdate("UPDATE `bankdate`.`person`\r\n" + 
				"SET\r\n" + 
				"`FIRST_NAME` = 'FirstNameUpdate',\r\n" + 
				"`LAST_NAME` = 'LastNameUpdate',\r\n" + 
				"`TELEPHON_NUMBER` = '700600500',\r\n" + 
				"`EMAIL` = 'testMailUpdate@test.pl',\r\n" + 
				"`PASSWORD` = 'testPasswordUpdate'\r\n" + 
				"WHERE `idPerson` = 100;");
		DBUtil.dbExecuteUpdate("DELETE FROM `bankdate`.`person`\r\n" + 
				"WHERE `idPerson` = 100;");
	}
	
	@Test
	public void shoudlReturnCorrectDate() throws ClassNotFoundException, SQLException {
		DBUtil.dbExecuteUpdate("DELETE FROM `bankdate`.`person`\r\n" + 
				"WHERE `idPerson` = 101;");
		
		DBUtil.dbExecuteUpdate("INSERT INTO `bankdate`.`person`\r\n" + 
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
				"('101',\r\n" + 
				" 'TestFirstName',\r\n" + 
				" 'TestLastName',\r\n" + 
				" '12345678910', \r\n" +
				" 'TestStreet 37',\r\n" + 
				" '500600700',\r\n" + 
				" 'testMail@test.pl',\r\n" + 
				" 'testLogin',\r\n" + 
				" 'testPassword');");
		
		ResultSet resultSet = DBUtil.dbExecuteQuery("SELECT * FROM person");
		if(resultSet.next()) {
			assertEquals(101, resultSet.getInt(1));
			assertEquals("TestFirstName", resultSet.getString(2));
			assertEquals("TestLastName", resultSet.getString(3));
			assertEquals("12345678910", resultSet.getString(4));
			assertEquals("TestStreet 37", resultSet.getString(5));
			assertEquals("500600700", resultSet.getString(6));
			assertEquals("testMail@test.pl", resultSet.getString(7));
			assertEquals("testLogin", resultSet.getString(8));
			assertEquals("testPassword", resultSet.getString(9));
		}
		
		DBUtil.dbExecuteUpdate("DELETE FROM `bankdate`.`person`\r\n" + 
				"WHERE `idPerson` = 101;");
	}
}
