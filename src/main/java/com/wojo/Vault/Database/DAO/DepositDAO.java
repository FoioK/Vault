package com.wojo.Vault.Database.DAO;

import com.wojo.Vault.Database.Model.Deposits;

import java.util.List;

public interface DepositDAO {

    <T extends Deposits> Integer insertDepositToDB(T deposit);

    List<Deposits> getAllDeposits(Integer idAccount);
}
