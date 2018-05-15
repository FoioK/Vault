package com.wojo.Vault.Service;

import com.wojo.Vault.Database.Model.Account;
import com.wojo.Vault.Database.Model.Deposit;

import java.math.BigDecimal;
import java.util.List;

public interface DepositService {

    boolean createDeposit(Account account, BigDecimal amount, Deposit.DepositType type);

    List<Deposit> getActiveDeposits(Account account);
}
