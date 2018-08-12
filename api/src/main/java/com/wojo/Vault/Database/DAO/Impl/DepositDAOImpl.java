package com.wojo.Vault.Database.DAO.Impl;

import com.wojo.Vault.Database.DAO.DepositDAO;
import com.wojo.Vault.Database.DBManager;
import com.wojo.Vault.Database.Model.Deposit;
import com.wojo.Vault.Database.Model.DepositsModel.LongDeposit;
import com.wojo.Vault.Database.Model.DepositsModel.MiddleDeposit;
import com.wojo.Vault.Database.Model.DepositsModel.ShortDeposit;
import com.wojo.Vault.Exception.ExecuteStatementException;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

public class DepositDAOImpl implements DepositDAO {

    @Override
    public <T extends Deposit> boolean insertDepositToDB(T deposit, BigDecimal newValue) {
        String insertDepositStatement = "INSERT INTO deposit " +
                "(ACCOUNT_ID, AMOUNT, START_DATE, END_DATE, TYPE, IS_ACTIVE) " +
                "VALUES " +
                "(?, ?, ?, ?, ?, ?)";
        String updateAccountValueStatement = "UPDATE account " +
                "SET VALUE = ? " +
                "WHERE ACCOUNT_ID = ?";

        Map<List<Object>, String> dataToUpdate = new HashMap<>();
        dataToUpdate.put(Arrays.asList(
                String.valueOf(deposit.getAccountId()),
                deposit.getDepositAmount().toString(),
                deposit.getStartDate().toString(),
                deposit.getEndDate().toString(),
                deposit.getDepositType().toString(),
                1 + ""), insertDepositStatement);

        dataToUpdate.put(Arrays.asList(
                newValue.toString(),
                String.valueOf(deposit.getAccountId())), updateAccountValueStatement);

        try {
            return DBManager.dbExecuteTransactionUpdate(dataToUpdate);
        } catch (ExecuteStatementException e) {
            System.out.println("insert deposit: " + e.errorCode());
        }

        return false;
    }

    @Override
    public List<Deposit> findAll(String accountId) {
        String queryStatement = "SELECT * FROM deposit WHERE ACCOUNT_ID = ?";

        try {
            ResultSet resultSet =
                    DBManager.dbExecuteQuery(queryStatement, Collections.singletonList(String.valueOf(accountId)));

            return getDepositLists(resultSet, accountId);
        } catch (ExecuteStatementException e) {
            System.out.println("get all deposit: " + e.errorCode());
        } catch (SQLException e) {
            System.out.println("ResultSet error: get all deposit");
        }

        return null;
    }

    private List<Deposit> getDepositLists(ResultSet resultSet, String accountId) throws SQLException {
        List<Deposit> allDeposits = new ArrayList<>();

        while (resultSet.next()) {
            String type = resultSet.getString("TYPE");
            String idDeposit = resultSet.getString("DEPOSIT_ID");
            BigDecimal depositAmount = resultSet.getBigDecimal("AMOUNT");
            LocalDateTime startDate =
                    LocalDateTime.ofInstant(
                            Instant.ofEpochMilli(resultSet.getTimestamp("START_DATE").getTime()),
                            TimeZone.getDefault().toZoneId());

            if (type.equals(Deposit.DepositType.Short.toString())) {
                allDeposits.add(new ShortDeposit(idDeposit, accountId, depositAmount, startDate));
            } else if (type.equals(Deposit.DepositType.Middle.toString())) {
                allDeposits.add(new MiddleDeposit(idDeposit, accountId, depositAmount, startDate));
            } else if (type.equals(Deposit.DepositType.Long.toString())) {
                allDeposits.add(new LongDeposit(idDeposit, accountId, depositAmount, startDate));
            } else {
                return null;
            }
        }

        return allDeposits;
    }

    @Override
    public <T extends Deposit> boolean archiveDeposit(T deposit, BigDecimal newValue) {
        String updateDepositStatement = "UPDATE deposit " +
                "SET IS_ACTIVE = 0 " +
                "WHERE DEPOSIT_ID = ?";

        String updateAccountValueStatement = "UPDATE account " +
                "SET VALUE = ? " +
                "WHERE ACCOUNT_ID = ?";

        Map<List<Object>, String> dataToUpdate = new HashMap<>();

        dataToUpdate.put(Collections.singletonList(String.valueOf(deposit.getDepositId())), updateDepositStatement);
        dataToUpdate.put(Arrays.asList(newValue.toString(), String.valueOf(deposit.getAccountId())),
                updateAccountValueStatement);

        try {
            return DBManager.dbExecuteTransactionUpdate(dataToUpdate);
        } catch (ExecuteStatementException e) {
            System.out.println("archive deposit: " + e.errorCode());
        }

        return false;
    }
}
