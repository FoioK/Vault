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
    public <T extends Deposit> Integer insertDepositToDB(T deposit) {
        String updateStatement = "INSERT INTO deposit " +
                "(ID_ACCOUNT, AMOUNT, DATE_START, DATE_END, TYPE, IS_ACTIVE)" +
                "VALUES " +
                "(?, ?, ?, ?, ?, ?)";
        try {
            return DBManager.dbExecuteUpdate(updateStatement,
                    Arrays.asList(String.valueOf(deposit.getIdAccount()),
                            deposit.getDepositAmount().toString(),
                            deposit.getStartDate().toString(),
                            deposit.getEndDate().toString(),
                            deposit.getDepositType().toString(),
                            1 + ""));
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
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
    public Integer archiveDeposit(Integer idDeposit) {
        String updateStatement = "UPDATE deposit " +
                "SET IS_ACTIVE = 0 " +
                "WHERE ID_DEPOSIT = ?";
        try {
            return DBManager.dbExecuteUpdate(updateStatement,
                    Collections.singletonList(String.valueOf(idDeposit)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
