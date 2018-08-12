package com.wojo.Vault.Database.Model.DepositsModel;

import com.wojo.Vault.Database.Model.Deposit;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LongDeposit extends Deposit {

    public static final Integer PERCENT = 9;
    public static final BigDecimal MINIMAL_AMOUNT = BigDecimal.valueOf(500);
    public static final Integer NUMBER_OF_DAYS = 30;

    public LongDeposit(String idDeposit, String idAccount, BigDecimal depositAmount, LocalDateTime startDate) {
        super(idDeposit, idAccount, depositAmount, startDate);
        this.setData(startDate);
    }

    public LongDeposit(String idAccount, BigDecimal depositAmount, LocalDateTime startDate) {
        super(idAccount, depositAmount, startDate);
        this.setData(startDate);
    }

    private void setData(LocalDateTime startDate) {
        super.setPercent(PERCENT);
        super.setMinimalAmount(MINIMAL_AMOUNT);
        super.setNumberOfDays(NUMBER_OF_DAYS);
        super.setEndDate(startDate.plusDays(NUMBER_OF_DAYS));
        super.setDepositType(DepositType.Long);
    }

    public static String depositDescription() {
        return PERCENT + "% | MIN: " + MINIMAL_AMOUNT + " | " + NUMBER_OF_DAYS + " day";
    }
}
