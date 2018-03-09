package com.wojo.Vault.Database.DAO.Impl;

import com.wojo.Vault.Database.DAO.DepositDAO;
import com.wojo.Vault.Database.DBManager;
import com.wojo.Vault.Database.Model.Deposits;

import java.sql.SQLException;
import java.util.Arrays;

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
}
