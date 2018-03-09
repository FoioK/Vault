package com.wojo.Vault.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.wojo.Vault.Database.Model.Deposits;
import com.wojo.Vault.Database.Model.DepositsModel.LongDeposit;
import com.wojo.Vault.Database.Model.DepositsModel.MiddleDeposit;
import com.wojo.Vault.Database.Model.DepositsModel.ShortDeposit;
import com.wojo.Vault.Service.AccountService;
import com.wojo.Vault.Service.DepositService;
import com.wojo.Vault.Service.impl.AccountServiceImpl;
import com.wojo.Vault.Service.impl.DepositServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ResourceBundle;

public class DepositsController {

    private RootController rootController;
    private AccountService accountService = new AccountServiceImpl();
    private DepositService depositService = new DepositServiceImpl();
    private Deposits.DepositType depositType;

    @FXML
    private JFXButton backToDesktopPane;

    @FXML
    private ChoiceBox<String> depositsTypeBox;

    @FXML
    private JFXTextField amount;

    @FXML
    private JFXButton openDeposit;

    @FXML
    private Label badTypeMessage;

    @FXML
    private Label badAmountMessage;

    @FXML
    void initialize() {
        setErrorMessagesVisibleFalse();
        addEventHandlers();

        setDepositsTypeBox();
    }

    private void setErrorMessagesVisibleFalse() {
        badTypeMessage.setVisible(false);
        badAmountMessage.setVisible(false);
    }

    private void setDepositsTypeBox() {
        ResourceBundle bundle = ResourceBundle.getBundle("Bundles.messages");
        ObservableList<String> depositsList = FXCollections.observableArrayList(
                bundle.getString("Desktop.Deposits.selectDepositName"),
                ShortDeposit.depositDescription(),
                MiddleDeposit.depositDescription(),
                LongDeposit.depositDescription());
        depositsTypeBox.setItems(depositsList);
        depositsTypeBox.setValue(bundle.getString("Desktop.Deposits.selectDepositName"));
    }

    private void addEventHandlers() {
        backToDesktopPane.addEventHandler(ActionEvent.ACTION, e -> rootController.loadDesktopPane());

        openDeposit.addEventHandler(ActionEvent.ACTION, e -> openDepositProcess());
    }

    private void openDepositProcess() {
        setErrorMessagesVisibleFalse();
        if (depositsTypeBox.getSelectionModel().isSelected(0)) {
            badTypeMessage.setVisible(true);
            return;
        }
        String depositDescription = depositsTypeBox.getSelectionModel().getSelectedItem();
        BigDecimal amountValue;
        try {
            amountValue = BigDecimal.valueOf(Double.valueOf(amount.getText()))
                    .setScale(2, RoundingMode.CEILING);
            if (amountValue.compareTo(accountService.getAccountValue()) > 0
                    || !checkMinValue(amountValue, depositDescription)) {
                badAmountMessage.setVisible(true);
                return;
            }
        } catch (NumberFormatException e) {
            badAmountMessage.setVisible(true);
            return;
        }
        depositService.createDeposit(amountValue, depositType);
    }

    private boolean checkMinValue(BigDecimal amountValue, String depositDescription) {
        BigDecimal minimalAmount = null;
        if (depositDescription.equals(ShortDeposit.depositDescription())) {
            minimalAmount = ShortDeposit.MINIMAL_AMOUNT;
            depositType = Deposits.DepositType.Short;
        } else if (depositDescription.equals(MiddleDeposit.depositDescription())) {
            minimalAmount = MiddleDeposit.MINIMAL_AMOUNT;
            depositType = Deposits.DepositType.Middle;
        } else if (depositDescription.equals(LongDeposit.depositDescription())) {
            minimalAmount = LongDeposit.MINIMAL_AMOUNT;
            depositType = Deposits.DepositType.Long;
        }
        if (minimalAmount == null) {
            return false;
        }
        return amountValue.compareTo(minimalAmount) >= 0;
    }

    public void setRootController(RootController rootController) {
        this.rootController = rootController;
    }
}
