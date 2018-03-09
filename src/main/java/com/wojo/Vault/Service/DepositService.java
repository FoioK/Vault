package com.wojo.Vault.Service;

import com.wojo.Vault.Database.Model.Deposits;

import java.math.BigDecimal;

public interface DepositService {

    boolean createDeposit(BigDecimal amount, Deposits.DepositType type);
}
