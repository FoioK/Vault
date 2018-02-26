package com.wojo.Vault.Service.impl;

import com.wojo.Vault.Database.DAO.Impl.PaymentDAOImpl;
import com.wojo.Vault.Database.DAO.PaymentDAO;
import com.wojo.Vault.Database.Model.CashFlow;
import com.wojo.Vault.Database.Model.Payment;
import com.wojo.Vault.Database.Model.Person;
import com.wojo.Vault.Service.CashFlowService;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class CashFlowServiceImpl implements CashFlowService {

    private PaymentDAO paymentDAO = new PaymentDAOImpl();

    private Integer activeIdAccount = 0;

    @Override
    public List<CashFlow> getLastThreeMonthCashFlow() {
        List<CashFlow> cashFlowList = new ArrayList<>(3);

        Month currentMonth = LocalDate.now().getMonth();
        final Integer CURRENT_MONTH_INDEX = 0;
        final Integer A_MONTH_AGO_INDEX = 1;
        final Integer A_TWO_MONTH_AGO_INDEX = 2;


        List<Payment> lastThreeMonthPayment = paymentDAO
                .getLastThreeMonthPayment(Person.getAccounts().get(activeIdAccount).getIdAccount());

        cashFlowList.add(new CashFlow(currentMonth));
        cashFlowList.add(new CashFlow(currentMonth.minus(1)));
        cashFlowList.add(new CashFlow(currentMonth.minus(2)));

        lastThreeMonthPayment.forEach(payment -> {
            Month paymentMonth = payment.getDate()
                    .toInstant().atZone(ZoneId.systemDefault())
                    .toLocalDate().getMonth();
            if (paymentMonth.compareTo(currentMonth) == 0) {
                cashFlowList.get(CURRENT_MONTH_INDEX).addPayment(payment);
            } else if (paymentMonth.compareTo(currentMonth.minus(1)) == 0) {
                cashFlowList.get(A_MONTH_AGO_INDEX).addPayment(payment);
            } else if (paymentMonth.compareTo(currentMonth.minus(2)) == 0) {
                cashFlowList.get(A_TWO_MONTH_AGO_INDEX).addPayment(payment);
            }
        });
        return cashFlowList;
    }
}
