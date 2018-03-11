package com.wojo.Vault.Service;

import com.wojo.Vault.Database.Model.Deposit;

import java.math.BigDecimal;
import java.util.List;

public interface DepositService {

    boolean createDeposit(BigDecimal amount, Deposit.DepositType type);

    List<Deposit> getActiveDeposits();
}
