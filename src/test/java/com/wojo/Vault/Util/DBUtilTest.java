package com.wojo.Vault.Util;

import org.junit.Ignore;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DBUtilTest {

    /**
     * TestAccount in database
     * idPeron:1
     * FIRST_NAME:TestAccount
     * LAST_NAME:TetAccount
     * PERSON_ID:00000000000
     * ADDRESS:TestAccount
     * TELEPHON_NUMBER:123456789
     * EMAIL:TestAccount
     * LOGIN:ABCDEFGHI
     * PASSWORD:Test
     */

    @Ignore
    @Test
    public void connectionTest() {
//        try {
//            DBUtil.dbConnect();
//        } catch (ClassNotFoundException e) {
//            fail("ClassNotFoundException");
//        } catch (SQLException e) {
//            fail("SQLException");
//        }
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
        String queryStatementNewValue = "UPDATE `bankdate`.`person`\n" +
                "SET\n" +
                "`FIRST_NAME` = 'TestN',\n" +
                "`LAST_NAME` = 'TestL',\n" +
                "`PERSON_ID` = '11111111111',\n" +
                "`ADDRESS` = 'TestA',\n" +
                "`TELEPHONE_NUMBER` = '98765432',\n" +
                "`EMAIL` = 'TestE',\n" +
                "`PASSWORD` = 'TestP'\n" +
                "WHERE `idPerson` = '1';";
        DBUtil.dbExecuteUpdate(queryStatementNewValue);

        String queryStatementOldValue = "UPDATE `bankdate`.`person`\n" +
                "SET\n" +
                "`FIRST_NAME` = 'TestAccount',\n" +
                "`LAST_NAME` = 'TestAccount',\n" +
                "`PERSON_ID` = '00000000000',\n" +
                "`ADDRESS` = 'TestAccount',\n" +
                "`TELEPHONE_NUMBER` = '123456789',\n" +
                "`EMAIL` = 'TestAccount',\n" +
                "`PASSWORD` = 'Test'\n" +
                "WHERE `idPerson` = '1';";
        DBUtil.dbExecuteUpdate(queryStatementOldValue);
    }

    @Test
    public void shouldReturnCorrectDate() throws ClassNotFoundException, SQLException {
        String queryStatement = " SELECT * FROM person WHERE idPerson = '1';";
        ResultSet resultSet = DBUtil.dbExecuteQuery(queryStatement);
        if (resultSet.next()) {
            assertEquals(1, resultSet.getInt(1));
            assertEquals("TestAccount", resultSet.getString(2));
            assertEquals("TestAccount", resultSet.getString(3));
            assertEquals("00000000000", resultSet.getString(4));
            assertEquals("TestAccount", resultSet.getString(5));
            assertEquals("123456789", resultSet.getString(6));
            assertEquals("TestAccount", resultSet.getString(7));
            assertEquals("ABCDEFGHI", resultSet.getString(8));
            assertEquals("Test", resultSet.getString(9));
        }
    }
}
