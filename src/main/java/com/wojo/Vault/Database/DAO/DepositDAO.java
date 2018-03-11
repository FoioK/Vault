package com.wojo.Vault.Database.DAO;

import com.wojo.Vault.Database.Model.Deposit;

import java.util.List;

public interface DepositDAO {

    <T extends Deposit> Integer insertDepositToDB(T deposit);

    List<Deposit> getAllDeposits(Integer idAccount);

    Integer archiveDeposit(Integer idDeposit);
}
