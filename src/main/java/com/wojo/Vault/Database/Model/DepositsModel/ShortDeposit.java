package com.wojo.Vault.Database.Model.DepositsModel;

import com.wojo.Vault.Database.Model.Deposits;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ShortDeposit extends Deposits {

    public static final Double PERCENT = 0.5d;
    public static final BigDecimal MINIMAL_AMOUNT = BigDecimal.valueOf(2000);
    public static final Integer NUMBER_OF_DAYS = 1;

    public ShortDeposit(Integer idAccount, BigDecimal depositAmount, LocalDateTime startDate) {
        super(idAccount, depositAmount, startDate);
        super.setPercent(PERCENT);
        super.setMinimalAmount(MINIMAL_AMOUNT);
        super.setNumberOfDays(NUMBER_OF_DAYS);
        super.setEndDate(startDate.plusDays(NUMBER_OF_DAYS));
    }
}
