package com.wojo.Vault.Database.Model.DepositsModel;

import com.wojo.Vault.Database.Model.Deposits;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LongDeposit extends Deposits {

    public static final Double PERCENT = 0.9d;
    public static final BigDecimal MINIMAL_AMOUNT = BigDecimal.valueOf(500);
    public static final Integer NUMBER_OF_DAYS = 30;

    public LongDeposit(Integer idAccount, BigDecimal depositAmount, LocalDateTime startDate) {
        super(idAccount, depositAmount, startDate);
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
