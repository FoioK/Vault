package com.wojo.Vault.Database.DAO.Impl;

import com.wojo.Vault.Database.DAO.AccountDAO;
import com.wojo.Vault.Database.DBManager;
import com.wojo.Vault.Database.Model.Account;
import com.wojo.Vault.Exception.ExecuteStatementException;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AccountDAOImpl implements AccountDAO {

    @Override
    public List<Account> findAllByPersonId(String personId) {
        String queryStatement = "SELECT *  FROM account WHERE PERSON_ID LIKE ?";

        ResultSet resultSet;
        try {
            resultSet = DBManager.dbExecuteQuery(queryStatement, Collections.singletonList(personId));
            return resultSet.next() ? getAccountList(resultSet) : new ArrayList<>();
        } catch (ExecuteStatementException e) {
            System.out.println("find all accounts by person id: " + e.errorCode());
        } catch (SQLException e) {
            System.out.println("ResultSet error: Find all accounts");
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private List<Account> getAccountList(ResultSet resultSet) throws SQLException {
        List<Account> accountList = new ArrayList<>();

        do {
            accountList.add(new Account(
                    resultSet.getString("ACCOUNT_ID"),
                    resultSet.getString("PERSON_ID"),
                    resultSet.getString("NUMBER"),
                    new BigDecimal(resultSet.getString("VALUE"))
            ));
        } while (resultSet.next());

        return accountList;
    }

    @Override
    public String findIdByNumber(String number) {
        String queryStatement = "SELECT ACCOUNT_ID FROM account WHERE NUMBER LIKE ?";

        ResultSet resultSet;
        try {
            resultSet = DBManager.dbExecuteQuery(queryStatement, Collections.singletonList(number));

            return resultSet.next() ? resultSet.getString("ACCOUNT_ID") : "";
        } catch (ExecuteStatementException e) {
            System.out.println("find account id by number: " + e.errorCode());
        } catch (SQLException e) {
            System.out.println("ResultSet error: find account id by number");
        }

        return "";
    }

    @Override
    public boolean isNumberExist(String number) {
        String queryStatement = "SELECT COUNT(NUMBER) FROM account WHERE NUMBER LIKE ?";

        ResultSet resultSet;
        try {
            resultSet = DBManager.dbExecuteQuery(queryStatement, Collections.singletonList(number));
            return resultSet.next() && resultSet.getInt(1) > 0;
        } catch (ExecuteStatementException e) {
            System.out.println("check number: " + e.errorCode());
        } catch (SQLException e) {
            System.out.println("ResultSet error: check number");
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean insertAccount(Account account) {
        String updateStatement = "INSERT INTO account (PERSON_ID, NUMBER, VALUE) VALUES (?, ?, ?)";

        List<String> dataToUpdate = Arrays.asList(
                account.getPersonId(),
                account.getNumber(),
                account.getValue().toString()
        );

        try {
            return DBManager.dbExecuteUpdate(updateStatement, dataToUpdate) == 1;
        } catch (ExecuteStatementException e) {
            System.out.println("insert account: " + e.errorCode());
        }

        return false;
    }

    @Override
    public boolean setValue(String number, BigDecimal newValue) {
        String updateStatement = "UPDATE account " +
                "SET VALUE = ? " +
                "WHERE NUMBER LIKE ?";
        try {
            return DBManager.dbExecuteUpdate(updateStatement, Arrays.asList(newValue.toString(), number)) == 1;
        } catch (ExecuteStatementException e) {
            System.out.println("set account value: " + e.errorCode());
        }

        return false;
    }

    /**
     *
     * @return account value, when don't find account by number return -100 or -1000 if the case of an exception.
     */
    @Override
    public BigDecimal getValueByNumber(String number) {
        String queryStatement = "SELECT VALUE FROM account " +
                "WHERE NUMBER LIKE ?";

        ResultSet resultSet;
        try {
            resultSet = DBManager.dbExecuteQuery(queryStatement, Collections.singletonList(number));
            return resultSet.next() ?
                    new BigDecimal(resultSet.getString("VALUE")) : new BigDecimal("-100");
        } catch (ExecuteStatementException e) {
            System.out.println("get account value by number: " + e.errorCode());
        } catch (SQLException e) {
            System.out.println("ResultSet error: get account value by number");
            e.printStackTrace();
        }

        return new BigDecimal("-1000");
    }
}
