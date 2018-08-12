package com.wojo.Vault.Service.impl;

import com.wojo.Vault.Database.DAO.DepositDAO;
import com.wojo.Vault.Database.DAO.Impl.DepositDAOImpl;
import com.wojo.Vault.Database.Model.Account;
import com.wojo.Vault.Database.Model.Deposit;
import com.wojo.Vault.Database.Model.DepositsModel.LongDeposit;
import com.wojo.Vault.Database.Model.DepositsModel.MiddleDeposit;
import com.wojo.Vault.Database.Model.DepositsModel.ShortDeposit;
import com.wojo.Vault.Service.DepositService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DepositServiceImpl implements DepositService {

    private DepositDAO depositDAO = new DepositDAOImpl();

    @Override
    public boolean createDeposit(Account account, BigDecimal amount, Deposit.DepositType type) {
        if (amount.compareTo(account.getValue()) > 0) {
            return false;
        }

        Deposit deposit;
        if (type == Deposit.DepositType.Short) {
            deposit = new ShortDeposit(account.getAccountId(), amount, LocalDateTime.now());
        } else if (type == Deposit.DepositType.Middle) {
            deposit = new MiddleDeposit(account.getAccountId(), amount, LocalDateTime.now());
        } else if (type == Deposit.DepositType.Long) {
            deposit = new LongDeposit(account.getAccountId(), amount, LocalDateTime.now());
        } else {
            return false;
        }

        BigDecimal newAccountValue = account.getValue().subtract(amount)
                .setScale(2, RoundingMode.HALF_EVEN);
        if (depositDAO.insertDepositToDB(deposit, newAccountValue)) {
            account.setValue(newAccountValue);
            return true;
        }

        return false;
    }

    @Override
    public List<Deposit> getActiveDeposits(Account account) {
        if (account == null) {
            return new ArrayList<>();
        }

        List<Deposit> allActiveDeposits = depositDAO.findAll(account.getAccountId());
        if (allActiveDeposits == null || allActiveDeposits.size() == 0) {
            return new ArrayList<>();
        }

        List<Deposit> endDeposits = new ArrayList<>();
        allActiveDeposits.parallelStream()
                .filter(this::compareDate)
                .forEach(endDeposits::add);

        if (endDeposits.size() != 0) {
            calculateAndGetProfit(endDeposits, account);
        }

        allActiveDeposits.removeAll(endDeposits);
        allActiveDeposits.sort(Comparator.comparing(Deposit::getStartDate));
        Collections.reverse(allActiveDeposits);

        return allActiveDeposits;
    }

    private boolean compareDate(Deposit deposit) {
        LocalDateTime endDate = deposit.getEndDate();

        return endDate.toLocalDate().compareTo(LocalDate.now()) < 0 ||
                endDate.toLocalDate().compareTo(LocalDate.now()) == 0 &&
                        endDate.getHour() <= LocalDateTime.now().getHour();
    }

    private void calculateAndGetProfit(List<Deposit> endDeposits, Account account) {
        Runnable r = () -> endDeposits.parallelStream().forEach(deposit -> {
            BigDecimal newValue = account.getValue().add(getEndDepositAmount(deposit));
            if (depositDAO.archiveDeposit(deposit, newValue)) {
                account.setValue(newValue);
            }
        });
        new Thread(r).start();
    }

    private BigDecimal getEndDepositAmount(Deposit deposit) {
        return deposit.getDepositAmount().add(deposit.getProfit())
                .setScale(2, RoundingMode.HALF_EVEN);
    }
}