package com.wojo.Vault.Database.DAO;

import com.wojo.Vault.Database.Model.Deposit;

import java.math.BigDecimal;
import java.util.List;

public interface DepositDAO {

    <T extends Deposit> boolean insertDepositToDB(T deposit, BigDecimal newValue);

    List<Deposit> findAll(String accountId);

    <T extends Deposit> boolean archiveDeposit(T deposit, BigDecimal newValue);
}
