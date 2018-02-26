package com.wojo.Vault.Service;

import com.wojo.Vault.Database.Model.CashFlow;

import java.util.List;

public interface CashFlowService {

    List<CashFlow> getLastThreeMonthCashFlow();

    CashFlow getLastMothFlow();
}
