package com.wojo.Vault.Service.impl;

import com.wojo.Vault.Database.DBManager;
import com.wojo.Vault.Database.Model.Account;
import com.wojo.Vault.Database.Model.Payment;
import com.wojo.Vault.Database.Model.Person;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

public class PaymentServiceImplTest {

    private PaymentServiceImpl paymentService = new PaymentServiceImpl();

    private static final String RECIPIENT_ID_ACCOUNT = 1 + "";

    @BeforeClass
    public static void setConnectionTestPath() {
        DBManager.setTestConnectionPath();
    }

    /**
     * Account value do not be lower than payment value
     */
    @Test(expected = NullPointerException.class)
    public void shouldNotReturnTransferInsideData() {
        Account senderAccount = new Account();
        senderAccount.setValue(BigDecimal.ZERO);
        paymentService.getTransferInsideData(senderAccount, RECIPIENT_ID_ACCOUNT, BigDecimal.TEN).size();
    }

    @Test(expected = NullPointerException.class)
    public void shouldNotReturnTransferOutsideData() {
        Account senderAccount = new Account();
        senderAccount.setValue(BigDecimal.ZERO);
        paymentService.getTransferOutsideData(senderAccount, BigDecimal.TEN).size();
    }

    @Test(expected = NullPointerException.class)
    public void getTransferInsideDataWithAccountNullPointer() {
        paymentService.getTransferInsideData(null, RECIPIENT_ID_ACCOUNT, BigDecimal.TEN).size();
    }

    @Test(expected = NullPointerException.class)
    public void getTransferOutsideDataWithAccountNullPointer() {
        paymentService.getTransferOutsideData(null, BigDecimal.TEN).size();
    }

    @Test(expected = NullPointerException.class)
    public void getInsertPaymentDataWithAccountNullPointer() {
        String recipient = "Recipient";
        String title = "title";
        paymentService.getInsertPaymentData(null, RECIPIENT_ID_ACCOUNT
                , recipient, title, BigDecimal.TEN).size();
    }

    private static final Integer ID_ACCOUNT = 3;

    @Test
    public void shouldReturnSortedPayments() {
        Account account = new Account();
        account.setIdAccount(ID_ACCOUNT);
        Person.setAccounts(Collections.singletonList(account));

        List<Payment> allPayments = paymentService.getAllPayment();
        for (int i = 0; i < allPayments.size() - 1; i++) {
            Date dateFirst = allPayments.get(i).getDate();
            Date dateSecond = allPayments.get(i + 1).getDate();
            assertTrue(dateFirst.compareTo(dateSecond) >= 0);
        }
    }
}