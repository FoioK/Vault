package com.wojo.Vault.Controller;

import com.jfoenix.controls.JFXButton;
import com.wojo.Vault.Service.AccountService;
import com.wojo.Vault.Service.PaymentService;
import com.wojo.Vault.Service.impl.AccountServiceImpl;
import com.wojo.Vault.Service.impl.PaymentServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class AccountsController {

    private RootController rootController;
    private DesktopController desktopController;

    private AccountService accountService = new AccountServiceImpl();
    private PaymentService paymentService = new PaymentServiceImpl();

    @FXML
    private JFXButton backToDesktopPane;

    @FXML
    private Label number;

    @FXML
    private Label value;

    @FXML
    private Label firstDate;

    @FXML
    private Label firstTitle;

    @FXML
    private Label firstAmount;

    @FXML
    private Label secondDate;

    @FXML
    private Label secondTitle;

    @FXML
    private Label secondAmount;

    @FXML
    private Label thirdDate;

    @FXML
    private Label thirdTitle;

    @FXML
    private Label thirdAmount;

    @FXML
    private JFXButton newTransfer;

    @FXML
    private JFXButton history;


    @FXML
    void initialize() {
        setLabelsText();
        addEventHandlers();
    }

    private void setLabelsText() {
        number.setText(accountService.getFormatAccountNumber(CurrentPerson.getActiveAccount()));
        value.setText(CurrentPerson.getActiveAccount().getValue().toString());
        setLastRecentTransactionText();
    }

    private void setLastRecentTransactionText() {
        List<List<Label>> labels = getLabelsList();
        final int[] index = {0};
        paymentService.getLastThreePayment(CurrentPerson.getActiveAccount().getAccountId())
                .stream()
                .limit(3)
                .forEach(payment -> {
                    labels.get(index[0]).get(0).setText(payment.getDate().toString());
                    labels.get(index[0]).get(1).setText(payment.getTitle());
                    BigDecimal amount = payment.getAmount();
                    if (amount.compareTo(BigDecimal.ZERO) < 0) {
                        labels.get(index[0]).get(2).setTextFill(Color.RED);
                    }
                    labels.get(index[0]).get(2).setText(amount.toString());
                    index[0] += 1;
                });
    }

    private List<List<Label>> getLabelsList() {
        return Arrays.asList(
                Arrays.asList(firstDate, firstTitle, firstAmount),
                Arrays.asList(secondDate, secondTitle, secondAmount),
                Arrays.asList(thirdDate, thirdTitle, thirdAmount));
    }

    private void addEventHandlers() {
        backToDesktopPane.addEventHandler(ActionEvent.ACTION, e -> rootController.loadDesktopPane());

        newTransfer.addEventHandler(ActionEvent.ACTION, e -> loadPayments());

        history.addEventHandler(ActionEvent.ACTION, e -> loadPaymentsHistory());
    }

    private void loadPayments() {
        desktopController.goToPayments();
    }

    private void loadPaymentsHistory() {
        desktopController.goToPaymentsHistory();
    }

    public void setRootController(RootController rootController) {
        this.rootController = rootController;
    }

    void setDesktopController(DesktopController desktopController) {
        this.desktopController = desktopController;
    }
}
