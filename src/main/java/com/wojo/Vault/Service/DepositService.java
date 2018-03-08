package com.wojo.Vault.Service;

import java.math.BigDecimal;

public interface DepositService {

    boolean createDeposit(BigDecimal amount, String type);
}
