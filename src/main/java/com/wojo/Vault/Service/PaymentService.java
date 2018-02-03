package com.wojo.Vault.Service;

import java.math.BigDecimal;

public interface PaymentService {

    boolean sendTransfer(String recipient, String recipientNumber, String title, BigDecimal value);
}
