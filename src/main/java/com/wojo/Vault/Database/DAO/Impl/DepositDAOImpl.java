package com.wojo.Vault.Database.DAO.Impl;

import com.wojo.Vault.Database.DAO.DepositDAO;
import com.wojo.Vault.Database.DBManager;
import com.wojo.Vault.Database.Model.Deposit;
import com.wojo.Vault.Database.Model.DepositsModel.LongDeposit;
import com.wojo.Vault.Database.Model.DepositsModel.MiddleDeposit;
import com.wojo.Vault.Database.Model.DepositsModel.ShortDeposit;

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
                "(ID_ACCOUNT, AMOUNT, DATE_START, DATE_END, TYPE, IS_ACTIVE)" +
                "VALUES " +
                "(?, ?, ?, ?, ?, ?)";
        String updateAccountValueStatement = "UPDATE accounts " +
                "SET value = ? " +
                "WHERE idAccount = ?";

        Map<List<Object>, String> dataToUpdate = new HashMap<>();

        dataToUpdate.put(Arrays.asList(
                String.valueOf(deposit.getIdAccount()),
                deposit.getDepositAmount().toString(),
                deposit.getStartDate().toString(),
                deposit.getEndDate().toString(),
                deposit.getDepositType().toString(),
                1 + ""), insertDepositStatement);

        dataToUpdate.put(Arrays.asList(newValue.toString(), String.valueOf(deposit.getIdAccount())),
                updateAccountValueStatement);

        try {
            return DBManager.dbExecuteTransactionUpdate(dataToUpdate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Deposit> getAllDeposits(Integer idAccount) {
        String queryStatement = "SELECT * FROM deposit WHERE ID_ACCOUNT = ?";
        List<Deposit> allDeposits = new ArrayList<>();
        try {
            ResultSet resultSet = DBManager.dbExecuteQuery(queryStatement,
                    Collections.singletonList(String.valueOf(idAccount)));
            while (resultSet.next()) {

                String type = resultSet.getString("TYPE");
                Integer idDeposit = resultSet.getInt("ID_DEPOSIT");
                BigDecimal depositAmount = resultSet.getBigDecimal("AMOUNT");
                LocalDateTime startDate =
                        LocalDateTime.ofInstant(
                                Instant.ofEpochMilli(resultSet.getTimestamp("DATE_START").getTime()),
                                TimeZone.getDefault().toZoneId());

                if (type.equals(Deposit.DepositType.Short.toString())) {
                    allDeposits.add(new ShortDeposit(idDeposit, idAccount, depositAmount, startDate));
                } else if (type.equals(Deposit.DepositType.Middle.toString())) {
                    allDeposits.add(new MiddleDeposit(idDeposit, idAccount, depositAmount, startDate));
                } else if (type.equals(Deposit.DepositType.Long.toString())) {
                    allDeposits.add(new LongDeposit(idDeposit, idAccount, depositAmount, startDate));
                } else {
                    return null;
                }
            }
            return allDeposits;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public <T extends Deposit> boolean archiveDeposit(T deposit, BigDecimal newValue) {
        String updateDepositStatement = "UPDATE deposit " +
                "SET IS_ACTIVE = 0 " +
                "WHERE ID_DEPOSIT = ?";

        String updateAccountValueStatement = "UPDATE accounts " +
                "SET value = ? " +
                "WHERE idAccount = ?";

        Map<List<Object>, String> dataToUpdate = new HashMap<>();

        dataToUpdate.put(Collections.singletonList(String.valueOf(deposit.getIdDeposit())), updateDepositStatement);
        dataToUpdate.put(Arrays.asList(newValue.toString(), String.valueOf(deposit.getIdAccount())),
                updateAccountValueStatement);

        try {
            return DBManager.dbExecuteTransactionUpdate(dataToUpdate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
