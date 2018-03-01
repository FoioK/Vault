package com.wojo.Vault.Controller;

import com.jfoenix.controls.JFXButton;
import com.wojo.Vault.Controller.Loader.ViewLoader;
import com.wojo.Vault.Database.Model.Payment;
import com.wojo.Vault.Database.Model.Person;
import com.wojo.Vault.Main;
import com.wojo.Vault.Service.AccountService;
import com.wojo.Vault.Service.PaymentService;
import com.wojo.Vault.Service.impl.AccountServiceImpl;
import com.wojo.Vault.Service.impl.PaymentServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class DesktopController {
    private RootController rootController;

    private AccountService accountService = new AccountServiceImpl();
    private PaymentService paymentService = new PaymentServiceImpl();

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

        exit.addEventHandler(ActionEvent.ACTION, e -> exitApplication());

        logOut.addEventHandler(ActionEvent.ACTION, e -> logOutAction());
    }

    private void setLabelsText() {
        fullName.setText(Person.getFirstName() + " " + Person.getLastName());

        setAccountsLabel();
    }

    private void setAccountsLabel() {
        accountsNumber.setText(accountService.getFormatAccountNumber());

        Payment deposit = paymentService.getRecentDeposit();
        if (deposit != null) {
            recentDeposit.setText(deposit.getSenderName() + " " + deposit.getPaymentValue() + " PLN");
        }
        Payment debit = paymentService.getRecentDebit();
        if (debit != null) {
            recentDebit.setText(debit.getRecipientName() + " " + debit.getPaymentValue() + " PLN");
            recentDebit.setTextFill(Color.RED);
        }
    }

    private static final String CASH_FLOW_VIEW = "CashFlow";
    private static final String ACCOUNTS_VIEW = "Accounts";
    private static final String PAYMENTS_VIEW = "Payments";
    private static final String PAYMENTS_HISTORY_VIEW = "PaymentsHistory";

    protected void goToCashFlow() {
        FXMLLoader loader = ViewLoader.loadView(this.getClass(), CASH_FLOW_VIEW);
        AnchorPane pane = (AnchorPane) ViewLoader.loadPane(loader, 0, 60);
        CashFlowController controller = loader.getController();
        controller.setRootController(rootController);
        mainPaneSetScreen(pane);
    }

    protected void goToAccounts() {
        FXMLLoader loader = ViewLoader.loadView(this.getClass(), ACCOUNTS_VIEW);
        AnchorPane pane = (AnchorPane) ViewLoader.loadPane(loader, 0, 60);
        AccountsController controller = loader.getController();
        controller.setRootController(rootController);
        controller.setDesktopController(this);
        mainPaneSetScreen(pane);
    }

    protected void goToPayments() {
        FXMLLoader loader = ViewLoader.loadView(this.getClass(), PAYMENTS_VIEW);
        AnchorPane pane = (AnchorPane) ViewLoader.loadPane(loader, 0, 60);
        PaymentsController controller = loader.getController();
        controller.setRootController(rootController);
        mainPaneSetScreen(pane);
    }

    protected void goToPaymentsHistory() {
        FXMLLoader loader = ViewLoader.loadView(this.getClass(), PAYMENTS_HISTORY_VIEW);
        AnchorPane pane = (AnchorPane) ViewLoader.loadPane(loader, 0, 60);
        PaymentsHistoryController controller = loader.getController();
        controller.setRootController(this);
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
