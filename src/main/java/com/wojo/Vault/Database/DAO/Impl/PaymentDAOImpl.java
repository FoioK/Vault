package com.wojo.Vault.Database.DAO.Impl;

import com.wojo.Vault.Database.DAO.PaymentDAO;
import com.wojo.Vault.Database.DBManager;
import com.wojo.Vault.Database.Model.Payment;
import com.wojo.Vault.Exception.ExecuteStatementException;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

public class PaymentDAOImpl implements PaymentDAO {

    @Override
    public Map<List<String>, String> getTransferInsideData(String recipientAccountId
            , String senderAccountId, BigDecimal recipientNewValue, BigDecimal senderNewValue) {
        String updateStatement = "UPDATE account " +
                "SET VALUE = ? " +
                "WHERE ACCOUNT_ID = ?";

        Map<List<String>, String> dataToUpdate = new HashMap<>();
        dataToUpdate.put(Arrays.asList(senderNewValue.toString(), senderAccountId), updateStatement);
        dataToUpdate.put(Arrays.asList(recipientNewValue.toString(), recipientAccountId), updateStatement);

        return dataToUpdate.size() == 2 ? dataToUpdate : null;
    }

    @Override
    public Map<List<String>, String> getTransferOutsideData(String senderAccountId
            , BigDecimal senderNewValue) {
        String updateStatement = "UPDATE account " +
                "SET VALUE = ? " +
                "WHERE ACCOUNT_ID = ?";

        Map<List<String>, String> dataToUpdate = new HashMap<>();
        dataToUpdate.put(Arrays.asList(senderNewValue.toString(), senderAccountId), updateStatement);

        return dataToUpdate.size() == 1 ? dataToUpdate : null;
    }

    @Override
    public Map<List<String>, String> getInsertPaymentData(String senderAccountId
            , String recipientAccountId, String recipientName, String recipientNumber
            , BigDecimal amount, String title, LocalDateTime date) {

        String updateStatement = "INSERT INTO payment " +
                "(SENDER_ACCOUNT_ID, RECIPIENT_ACCOUNT_ID, RECIPIENT_NAME, RECIPIENT_NUMBER, AMOUNT, TITLE, DATA) " +
                "VALUES " +
                "(?, ?, ?, ?, ?, ?, ?)";

        List<String> paymentData = Arrays.asList(senderAccountId, recipientAccountId, recipientName,
                recipientNumber, amount.toString(), title, date.toString());

        Map<List<String>, String> dataToUpdate = new HashMap<>();
        dataToUpdate.put(paymentData, updateStatement);

        return dataToUpdate.size() == 1 ? dataToUpdate : null;
    }

    @Override
    public <T> boolean sendTransfer(Map<List<T>, String> dataToUpdate) {
        try {
            return DBManager.dbExecuteTransactionUpdate(dataToUpdate);
        } catch (ExecuteStatementException e) {
            System.out.println("send transfer: " + e.errorCode());
        }

        return false;
    }

    @Override
    public List<Payment> findAll(Integer idAccount) {
        String queryStatement = "SELECT * FROM payment " +
                "WHERE SENDER_ACCOUNT_ID = ? OR RECIPIENT_ACCOUNT_ID = ?";
        try {
            ResultSet resultSet = DBManager.dbExecuteQuery(queryStatement
                    , Arrays.asList(String.valueOf(idAccount), String.valueOf(idAccount)));

            return getPaymentList(resultSet, idAccount);
        } catch (ExecuteStatementException e) {
            System.out.println("get all payment: " + e.errorCode());
        }

        return new ArrayList<>();
    }

    @Override
    public List<Payment> findAllFromLastThreeMonth(Integer idAccount) {
        String queryStatement = "SELECT * FROM payment " +
                "WHERE DATE >= now() - INTERVAL 3 MONTH " +
                "AND (SENDER_ACCOUNT_ID = ? OR RECIPIENT_ACCOUNT_ID = ?)";

        ResultSet resultSet;
        try {
            resultSet = DBManager.dbExecuteQuery(queryStatement,
                    Arrays.asList(String.valueOf(idAccount), String.valueOf(idAccount)));

            return getPaymentList(resultSet, idAccount);
        } catch (ExecuteStatementException e) {
            System.out.println("get last three month payment: " + e.errorCode());
        }

        return new ArrayList<>();
    }

    private List<Payment> getPaymentList(ResultSet resultSet, Integer idAccount) {
        List<Payment> paymentList = new ArrayList<>();

        try {
            while (resultSet.next()) {
                BigDecimal value = resultSet.getInt("SENDER_ACCOUNT_ID") == idAccount ?
                        resultSet.getBigDecimal("AMOUNT").negate() :
                        resultSet.getBigDecimal("AMOUNT");

                LocalDateTime date = LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(resultSet.getTimestamp("CREATE_TIME").getTime()),
                        TimeZone.getDefault().toZoneId());

                Payment payment = new Payment(
                        resultSet.getString("PAYMENT_ID"),
                        resultSet.getString("SENDER_ACCOUNT_ID"),
                        resultSet.getString("RECIPIENT_ACCOUNT_ID"),
                        resultSet.getString("RECIPIENT_NAME"),
                        resultSet.getString("RECIPIENT_NUMBER"),
                        value,
                        resultSet.getString("TITLE"),
                        date
                );
                paymentList.add(payment);
            }

            return paymentList;
        } catch (SQLException e) {
            System.out.println("ResultSet error: get payment list");
        }

        return null;
    }
}
