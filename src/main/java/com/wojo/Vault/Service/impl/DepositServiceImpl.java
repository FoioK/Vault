package com.wojo.Vault.Service.impl;

import com.wojo.Vault.Database.DAO.AccountDAO;
import com.wojo.Vault.Database.DAO.DepositDAO;
import com.wojo.Vault.Database.DAO.Impl.AccountDAOImpl;
import com.wojo.Vault.Database.DAO.Impl.DepositDAOImpl;
import com.wojo.Vault.Database.Model.Account;
import com.wojo.Vault.Database.Model.Deposit;
import com.wojo.Vault.Database.Model.DepositsModel.LongDeposit;
import com.wojo.Vault.Database.Model.DepositsModel.MiddleDeposit;
import com.wojo.Vault.Database.Model.DepositsModel.ShortDeposit;
import com.wojo.Vault.Database.Model.Person;
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
    private AccountDAO accountDAO = new AccountDAOImpl();

    @Override
    public boolean createDeposit(BigDecimal amount, Deposit.DepositType type) {
        Integer activeAccountId = 0;
        Account account = Person.getAccounts().get(activeAccountId);
        Deposit deposit;
        if (amount.compareTo(account.getValue()) > 0) {
            return false;
        }
        if (type == Deposit.DepositType.Short) {
            deposit = new ShortDeposit(account.getIdAccount(), amount, LocalDateTime.now());
        } else if (type == Deposit.DepositType.Middle) {
            deposit = new MiddleDeposit(account.getIdAccount(), amount, LocalDateTime.now());
        } else if (type == Deposit.DepositType.Long) {
            deposit = new LongDeposit(account.getIdAccount(), amount, LocalDateTime.now());
        } else {
            return false;
        }

        BigDecimal newAccountValue = account.getValue().subtract(amount).setScale(2, RoundingMode.CEILING);
        if (depositDAO.insertDepositToDB(deposit, newAccountValue)) {
            account.setValue(newAccountValue);
            return true;
        }
        return false;
    }

    @Override
    public List<Deposit> getActiveDeposits() {
        List<Deposit> allActiveDeposits = this.getAllActiveDeposits();
        if (allActiveDeposits == null || allActiveDeposits.size() == 0) {
            return new ArrayList<>();
        }

        List<Deposit> endDeposits = new ArrayList<>();
        allActiveDeposits.parallelStream()
                .filter(this::compareDate)
                .forEach(endDeposits::add);
        if (endDeposits.size() != 0) {
            calculateAndGetProfit(endDeposits);
        }
        allActiveDeposits.removeAll(endDeposits);
        allActiveDeposits.sort(Comparator.comparing(Deposit::getStartDate));
        Collections.reverse(allActiveDeposits);
        return allActiveDeposits;
    }

    private List<Deposit> getAllActiveDeposits() {
        Integer activeIdAccount = 0;
        return depositDAO.getAllDeposits(Person.getAccounts().get(activeIdAccount).getIdAccount());
    }

    private boolean compareDate(Deposit deposit) {
        LocalDateTime endDate = deposit.getEndDate();

        return endDate.toLocalDate().compareTo(LocalDate.now()) < 0 ||
                endDate.toLocalDate().compareTo(LocalDate.now()) == 0 &&
                        endDate.getHour() <= LocalDateTime.now().getHour();
    }

    private void calculateAndGetProfit(List<Deposit> endDeposits) {
        Runnable r = () -> {
            Integer activeIdAccount = 0;
            Account account = Person.getAccounts().get(activeIdAccount);
            endDeposits.parallelStream().forEach(deposit -> {
                BigDecimal newValue = account.getValue().add(getEndDepositAmount(deposit));
                if (depositDAO.archiveDeposit(deposit, newValue)) {
                    account.setValue(newValue);
                }
            });
        };
        new Thread(r).start();
    }

    public BigDecimal getEndDepositAmount(Deposit deposit) {
        return deposit.getDepositAmount().add(deposit.getProfit()).setScale(2, RoundingMode.CEILING);
    }
}
