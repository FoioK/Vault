package com.wojo.Vault.Database.DAO;

import com.wojo.Vault.Database.Model.Deposits;

public interface DepositDAO {

    <T extends Deposits> Integer insertDepositToDB(T deposit);
}
