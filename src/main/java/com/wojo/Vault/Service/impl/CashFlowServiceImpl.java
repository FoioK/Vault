package com.wojo.Vault.Service.impl;

import com.wojo.Vault.Database.DAO.Impl.PaymentDAOImpl;
import com.wojo.Vault.Database.DAO.PaymentDAO;
import com.wojo.Vault.Database.Model.CashFlow;
import com.wojo.Vault.Database.Model.Payment;
import com.wojo.Vault.Service.CashFlowService;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class CashFlowServiceImpl implements CashFlowService {

    private PaymentDAO paymentDAO = new PaymentDAOImpl();

    @Override
    public List<CashFlow> getLastThreeMonthCashFlow(String accountId) {
        List<CashFlow> cashFlowList = new ArrayList<>(3);
        List<Payment> lastThreeMonthPayment = paymentDAO.findAllFromLastThreeMonth(accountId);

        cashFlowList.add(new CashFlow(LocalDate.now()));
        cashFlowList.add(new CashFlow(LocalDate.now().minusMonths(1)));
        cashFlowList.add(new CashFlow(LocalDate.now().minusMonths(2)));

        lastThreeMonthPayment.forEach(payment -> {
            Month paymentMonth = payment.getDate().getMonth();
            cashFlowList.stream()
                    .filter(flow -> flow.getMonth().compareTo(paymentMonth) == 0)
                    .forEach(flow -> flow.addPayment(payment));
        });

        return cashFlowList;
    }

    @Override
    public CashFlow getLastMothFlow(String accountId) {
        return this.getLastThreeMonthCashFlow(accountId)
                .stream()
                .filter(flow -> flow.getMonth().compareTo(LocalDate.now().getMonth()) == 0)
                .findFirst()
                .get();
    }
}
