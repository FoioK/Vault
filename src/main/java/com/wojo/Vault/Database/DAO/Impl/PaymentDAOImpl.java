package com.wojo.Vault.Database.DAO.Impl;

import com.wojo.Vault.Database.DAO.PaymentDAO;
import com.wojo.Vault.Database.DBManager;
import com.wojo.Vault.Database.Model.Payment;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PaymentDAOImpl implements PaymentDAO {

    @Override
    public Map<List<Object>, String> getTransferInsideData(String recipientIdAccount
            , String senderIdAccount, BigDecimal recipientNewValue, BigDecimal senderNewValue) {
        String updateStatement = "UPDATE accounts " +
                "SET value = ? " +
                "WHERE idAccount = ?";

        Map<List<Object>, String> dataToUpdate = new HashMap<>();
        dataToUpdate.put(Arrays.asList(senderNewValue, senderIdAccount), updateStatement);
        dataToUpdate.put(Arrays.asList(recipientNewValue, recipientIdAccount), updateStatement);
        return dataToUpdate.size() == 2 ? dataToUpdate : null;
    }

    @Override
    public Map<List<Object>, String> getTransferOutsideData(String senderIdAccount
            , BigDecimal senderNewValue) {
        String updateStatement = "UPDATE accounts " +
                "SET value = ? " +
                "WHERE idAccount = ?";

        Map<List<Object>, String> dataToUpdate = new HashMap<>();
        dataToUpdate.put(Arrays.asList(senderNewValue, senderIdAccount), updateStatement);
        return dataToUpdate.size() == 1 ? dataToUpdate : null;
    }

    @Override
    public Map<List<Object>, String> getInsertPaymentData(String senderIdAccount
            , String recipientIdAccount, String recipient, String sender
            , String title, BigDecimal value) {
        String updateStatement = "INSERT INTO payments " +
                "(idAccount, recipientIdAccount, recipientName, senderName, title, paymentValue, date) " +
                "VALUES " +
                "(?, ?, ?, ?, ?, ?, ?)";

        Map<List<Object>, String> dataToUpdate = new HashMap<>();
        dataToUpdate.put(Arrays.asList(senderIdAccount, recipientIdAccount, recipient, sender
                , title, value, new Date()), updateStatement);
        return dataToUpdate.size() == 1 ? dataToUpdate : null;
    }

    @Override
    public boolean sendTransfer(Map<List<Object>, String> dataToUpdate) {
        try {
            return DBManager.dbExecuteTransactionUpdate(dataToUpdate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Payment> getAllPayment(Integer idAccount) {
        List<Payment> allPayments = new ArrayList<>();
        String queryStatement = "SELECT * FROM payments " +
                "WHERE idAccount = ? OR recipientIdAccount = ?";
        try {
            ResultSet resultSet = DBManager.dbExecuteQuery(queryStatement
                    , Arrays.asList(String.valueOf(idAccount), String.valueOf(idAccount)));
            while (resultSet.next()) {
                BigDecimal value = resultSet.getInt("idAccount") == idAccount ?
                        resultSet.getBigDecimal("paymentValue").negate() :
                        resultSet.getBigDecimal("paymentValue");

                Payment payment = new Payment(
                        resultSet.getInt("idPayment"),
                        resultSet.getInt("idAccount"),
                        resultSet.getString("recipientName"),
                        resultSet.getString("senderName"),
                        resultSet.getString("title"),
                        value,
                        resultSet.getTimestamp("date")
                );
                allPayments.add(payment);
            }
            allPayments.sort(Comparator.comparing(Payment::getDate));
            Collections.reverse(allPayments);
            return allPayments;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
