package com.wojo.Vault.Service.impl;

import com.wojo.Vault.Database.DBManager;
import com.wojo.Vault.Database.Model.Account;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;

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
}