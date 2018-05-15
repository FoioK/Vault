package com.wojo.Vault.Controller;

import com.jfoenix.controls.JFXButton;
import com.wojo.Vault.Controller.Loader.ViewLoader;
import com.wojo.Vault.Database.Model.*;
import com.wojo.Vault.Exception.AppException;
import com.wojo.Vault.Exception.ErrorCode;
import com.wojo.Vault.Main;
import com.wojo.Vault.Service.AccountService;
import com.wojo.Vault.Service.CashFlowService;
import com.wojo.Vault.Service.PaymentService;
import com.wojo.Vault.Service.impl.AccountServiceImpl;
import com.wojo.Vault.Service.impl.CashFlowServiceImpl;
import com.wojo.Vault.Service.impl.PaymentServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.math.BigDecimal;

public class DesktopController {

    private Person person;
    private RootController rootController;

    private AccountService accountService = new AccountServiceImpl();
    private PaymentService paymentService = new PaymentServiceImpl();
    private CashFlowService cashFlowService = new CashFlowServiceImpl();

    @FXML
    private JFXButton dashboard;

    @FXML
    private JFXButton logOut;

    @FXML
    private JFXButton accountsLeft;

    @FXML
    private JFXButton paymentsLeft;

    @FXML
    public JFXButton cashFlowLeft;

    @FXML
    private Label fullName;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Label cashFlowMonth;

    @FXML
    private Label cashFlowYear;

    @FXML
    private Label cashFlowBalance;

    @FXML
    private Label cashFlowIncomes;

    @FXML
    private Label cashFlowExpenses;

    @FXML
    private JFXButton cashFlowCenter;

    @FXML
    private JFXButton accountsCenter;

    @FXML
    private JFXButton depositsLeft;

    @FXML
    private JFXButton depositsCenter;

    @FXML
    private Label accountsNumber;

    @FXML
    private Label recentDeposit;

    @FXML
    private Label recentDebit;

    @FXML
    private JFXButton paymentsCenter;

    @FXML
    private JFXButton exit;

    @FXML
    public void initialize() {
        person = CurrentPerson.getInstance();

        addEventHandlers();
        setLabelsText();
    }

    private void addEventHandlers() {
        dashboard.addEventHandler(ActionEvent.ACTION, e -> rootController.loadDesktopPane());

        cashFlowLeft.addEventHandler(ActionEvent.ACTION, e -> goToCashFlow());
        cashFlowCenter.addEventHandler(ActionEvent.ACTION, e -> goToCashFlow());

        accountsLeft.addEventHandler(ActionEvent.ACTION, e -> goToAccounts());
        accountsCenter.addEventHandler(ActionEvent.ACTION, e -> goToAccounts());

        paymentsLeft.addEventHandler(ActionEvent.ACTION, e -> goToPayments());
        paymentsCenter.addEventHandler(ActionEvent.ACTION, e -> goToPayments());

        depositsLeft.addEventHandler(ActionEvent.ACTION, e -> goToDeposits());
        depositsCenter.addEventHandler(ActionEvent.ACTION, e -> goToDeposits());

        exit.addEventHandler(ActionEvent.ACTION, e -> exitApplication());

        logOut.addEventHandler(ActionEvent.ACTION, e -> logOutAction());
    }

    private void setLabelsText() {
        fullName.setText(person.getFirstName() + " " + person.getLastName());

        setAccountsLabel();
    }

    private void setAccountsLabel() throws AppException {
        if (person.getAccountList().size() != 0) {
            accountsNumber.setText(accountService.getFormatAccountNumber(person.getAccountList().get(0)));
        } else {
            if (accountService.setAccounts(person)) {
                accountsNumber.setText(accountService.getFormatAccountNumber(person.getAccountList().get(0)));
            } else {
                if (!accountService.createAccount(new Account(person.getPersonId(), "", BigDecimal.ZERO))) {
                    throw new AppException("Set account error", ErrorCode.NO_ACCOUNT_FOR_PERSON);
                }
            }
        }

        String accountId = CurrentPerson.getActiveAccount().getAccountId();
        Payment recentDeposit = paymentService.getRecentDeposit(accountId);
        if (recentDeposit != null) {
            this.recentDeposit.setText(person.getLastName() + " " + person.getFirstName() + " "
                    + recentDeposit.getAmount() + " PLN");
        }

        Payment recentDebit = paymentService.getRecentDebit(accountId);
        if (recentDebit != null) {
            this.recentDebit.setText(recentDebit.getRecipientName() + " " + recentDebit.getAmount() + " PLN");
            this.recentDebit.setTextFill(Color.RED);
        }

        CashFlow lastMonthFlow = cashFlowService.getLastMothFlow(accountId);
        cashFlowMonth.setText(lastMonthFlow.getMonth().toString());
        cashFlowYear.setText(lastMonthFlow.getYear() + "");
        cashFlowIncomes.setText(lastMonthFlow.getIncomes().toString());
        cashFlowExpenses.setText(lastMonthFlow.getExpenses().toString());
        cashFlowBalance.setText(lastMonthFlow.getBalance().toString());
    }

    private static final String CASH_FLOW_VIEW = "CashFlow";
    private static final String ACCOUNTS_VIEW = "Accounts";
    private static final String PAYMENTS_VIEW = "Payments";
    private static final String PAYMENTS_HISTORY_VIEW = "PaymentsHistory";
    private static final String DEPOSITS_VIEW = "Deposits";

    private void goToCashFlow() {
        FXMLLoader loader = ViewLoader.loadView(this.getClass(), CASH_FLOW_VIEW);
        AnchorPane pane = (AnchorPane) ViewLoader.loadPane(loader, 0, 60);
        CashFlowController controller = loader.getController();
        controller.setRootController(rootController);
        mainPaneSetScreen(pane);
    }

    void goToAccounts() {
        FXMLLoader loader = ViewLoader.loadView(this.getClass(), ACCOUNTS_VIEW);
        AnchorPane pane = (AnchorPane) ViewLoader.loadPane(loader, 0, 60);
        AccountsController controller = loader.getController();
        controller.setRootController(rootController);
        controller.setDesktopController(this);
        mainPaneSetScreen(pane);
    }

    void goToPayments() {
        FXMLLoader loader = ViewLoader.loadView(this.getClass(), PAYMENTS_VIEW);
        AnchorPane pane = (AnchorPane) ViewLoader.loadPane(loader, 0, 60);
        PaymentsController controller = loader.getController();
        controller.setRootController(rootController);
        mainPaneSetScreen(pane);
    }

    void goToPaymentsHistory() {
        FXMLLoader loader = ViewLoader.loadView(this.getClass(), PAYMENTS_HISTORY_VIEW);
        AnchorPane pane = (AnchorPane) ViewLoader.loadPane(loader, 0, 60);
        PaymentsHistoryController controller = loader.getController();
        controller.setRootController(this);
        mainPaneSetScreen(pane);
    }

    private void goToDeposits() {
        FXMLLoader loader = ViewLoader.loadView(this.getClass(), DEPOSITS_VIEW);
        AnchorPane pane = (AnchorPane) ViewLoader.loadPane(loader, 0, 60);
        DepositsController depositsController = loader.getController();
        depositsController.setRootController(rootController);
        mainPaneSetScreen(pane);
    }

    private <T extends Parent> void mainPaneSetScreen(T pane) {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(pane);
    }

    private void exitApplication() {
        Main.exitApplication();
    }

    private void logOutAction() {
        rootController.loadLoginStep1();
    }

    public void setRootController(RootController rootController) {
        this.rootController = rootController;
    }
}
