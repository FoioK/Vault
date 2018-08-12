package com.wojo.Vault.Database.Model;

import com.wojo.Vault.Database.Model.DepositsModel.LongDeposit;
import com.wojo.Vault.Database.Model.DepositsModel.MiddleDeposit;
import com.wojo.Vault.Database.Model.DepositsModel.ShortDeposit;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class DepositTest {

    private static final String ACCOUNT_ID = "0";

    @Test
    public void shouldReturnCorrectProfitForShortDeposit() {
        final BigDecimal depositAmount = BigDecimal.valueOf(50000);
        final Integer percent = ShortDeposit.PERCENT;
        final Integer numberOfDays = ShortDeposit.NUMBER_OF_DAYS;

        ShortDeposit shortDeposit = new ShortDeposit(ACCOUNT_ID, depositAmount, LocalDateTime.now());

        Double profit = depositAmount.doubleValue() * percent / 100 * numberOfDays / 365;
        BigDecimal expectedProfit = BigDecimal.valueOf(profit).setScale(2, RoundingMode.HALF_EVEN);

        assertEquals(expectedProfit, shortDeposit.getProfit());
    }

    @Test
    public void shouldReturnCorrectProfitForMiddleDeposit() {
        final BigDecimal depositAmount = BigDecimal.valueOf(60000);
        final Integer percent = MiddleDeposit.PERCENT;
        final Integer numberOfDays = MiddleDeposit.NUMBER_OF_DAYS;

        MiddleDeposit middleDeposit = new MiddleDeposit(ACCOUNT_ID, depositAmount, LocalDateTime.now());

        Double profit = depositAmount.doubleValue() * percent / 100 * numberOfDays / 365;
        BigDecimal expectedProfit = BigDecimal.valueOf(profit).setScale(2, RoundingMode.HALF_EVEN);

        assertEquals(expectedProfit, middleDeposit.getProfit());
    }

    @Test
    public void shouldReturnCorrectProfitForLongDeposit() {
        final BigDecimal depositAmount = BigDecimal.valueOf(40000);
        final Integer percent = LongDeposit.PERCENT;
        final Integer numberOfDays = LongDeposit.NUMBER_OF_DAYS;

        LongDeposit longDeposit = new LongDeposit(ACCOUNT_ID, depositAmount, LocalDateTime.now());

        Double profit = depositAmount.doubleValue() * percent / 100 * numberOfDays / 365;
        BigDecimal expectedProfit = BigDecimal.valueOf(profit).setScale(2, RoundingMode.HALF_EVEN);

        assertEquals(expectedProfit, longDeposit.getProfit());
    }
}