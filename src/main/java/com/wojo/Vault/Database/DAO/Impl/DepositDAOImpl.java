package com.wojo.Vault.Database.DAO.Impl;

import com.wojo.Vault.Database.DAO.DepositDAO;
import com.wojo.Vault.Database.DBManager;
import com.wojo.Vault.Database.Model.Deposits;
import com.wojo.Vault.Database.Model.DepositsModel.LongDeposit;
import com.wojo.Vault.Database.Model.DepositsModel.MiddleDeposit;
import com.wojo.Vault.Database.Model.DepositsModel.ShortDeposit;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

public class DepositDAOImpl implements DepositDAO {

    @Override
    public <T extends Deposits> Integer insertDepositToDB(T deposit) {
        String updateStatement = "INSERT INTO deposit " +
                "(ID_ACCOUNT, AMOUNT, DATE_START, DATE_END, TYPE)" +
                "VALUES " +
                "(?, ?, ?, ?, ?)";
        try {
            return DBManager.dbExecuteUpdate(updateStatement,
                    Arrays.asList(String.valueOf(deposit.getIdAccount()),
                            deposit.getDepositAmount().toString(),
                            deposit.getStartDate().toString(),
                            deposit.getEndDate().toString(),
                            deposit.getDepositType().toString()));
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<Deposits> getAllDeposits(Integer idAccount) {
        String queryStatement = "SELECT * FROM deposit WHERE idAccount = ?";
        List<Deposits> allDeposits = new ArrayList<>();
        try {
            ResultSet resultSet = DBManager.dbExecuteQuery(queryStatement,
                    Collections.singletonList(String.valueOf(idAccount)));
            while (resultSet.next()) {

                String type = resultSet.getString("TYPE");
                BigDecimal depositAmount = resultSet.getBigDecimal("AMOUNT");
                LocalDateTime startDate =
                        LocalDateTime.ofInstant(
                                Instant.ofEpochMilli(resultSet.getTimestamp("DATE_START").getTime()),
                                TimeZone.getDefault().toZoneId());

                if (type.equals(Deposits.DepositType.Short.toString())) {
                    allDeposits.add(new ShortDeposit(idAccount, depositAmount, startDate));
                } else if (type.equals(Deposits.DepositType.Middle.toString())) {
                    allDeposits.add(new MiddleDeposit(idAccount, depositAmount, startDate));
                } else if (type.equals(Deposits.DepositType.Long.toString())) {
                    allDeposits.add(new LongDeposit(idAccount, depositAmount, startDate));
                } else {
                    return new ArrayList<>();
                }
            }
            return allDeposits;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}
