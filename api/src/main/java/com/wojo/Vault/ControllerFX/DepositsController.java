package com.wojo.Vault.ControllerFX;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.wojo.Vault.Database.Model.Account;
import com.wojo.Vault.Database.Model.Deposit;
import com.wojo.Vault.Database.Model.DepositsModel.LongDeposit;
import com.wojo.Vault.Database.Model.DepositsModel.MiddleDeposit;
import com.wojo.Vault.Database.Model.DepositsModel.ShortDeposit;
import com.wojo.Vault.Service.DepositService;
import com.wojo.Vault.Service.impl.DepositServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.ResourceBundle;

public class DepositsController {

    private RootController rootController;
    private DepositService depositService = new DepositServiceImpl();
    private Deposit.DepositType depositType;

    @FXML
    private JFXButton backToDesktopPane;

    @FXML
    private Label badTypeMessage;

    @FXML
    private JFXTextField amount;

    @FXML
    private ChoiceBox<String> depositsTypeBox;

    @FXML
    private JFXButton openDeposit;

    @FXML
    private Label badAmountMessage;

    @FXML
    private AnchorPane activeList;

    @FXML
    void initialize() {
        setDepositsTypeBox();

        setErrorMessagesVisibleFalse();
        addEventHandlers();

        this.showActiveDepositsList();
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

        Account currentAccount = CurrentPerson.getActiveAccount();
        String depositDescription = depositsTypeBox.getSelectionModel().getSelectedItem();
        BigDecimal amountValue;
        try {
            amountValue = BigDecimal.valueOf(Double.valueOf(amount.getText()))
                    .setScale(2, RoundingMode.CEILING);
            if (amountValue.compareTo(currentAccount.getValue()) > 0
                    || !checkMinValue(amountValue, depositDescription)) {
                badAmountMessage.setVisible(true);
                return;
            }
        } catch (NumberFormatException e) {
            badAmountMessage.setVisible(true);
            return;
        }

        depositService.createDeposit(currentAccount, amountValue, depositType);
        rootController.loadDesktopPane();
    }

    private boolean checkMinValue(BigDecimal amountValue, String depositDescription) {
        BigDecimal minimalAmount = null;
        if (depositDescription.equals(ShortDeposit.depositDescription())) {
            minimalAmount = ShortDeposit.MINIMAL_AMOUNT;
            depositType = Deposit.DepositType.Short;
        } else if (depositDescription.equals(MiddleDeposit.depositDescription())) {
            minimalAmount = MiddleDeposit.MINIMAL_AMOUNT;
            depositType = Deposit.DepositType.Middle;
        } else if (depositDescription.equals(LongDeposit.depositDescription())) {
            minimalAmount = LongDeposit.MINIMAL_AMOUNT;
            depositType = Deposit.DepositType.Long;
        }

        return minimalAmount != null && amountValue.compareTo(minimalAmount) >= 0;
    }

    private static final Integer PANE_WIDTH = 1050;
    private static final Integer HEADER_PANE_HEIGHT = 75;
    private static final Integer DEPOSIT_ROW_HEIGHT = 75;

    @SuppressWarnings("Duplicates")
    private void showActiveDepositsList() {
        List<Deposit> allActiveDeposits =
                depositService.getActiveDeposits(CurrentPerson.getActiveAccount());

        if (allActiveDeposits.size() > 3) {
            activeList.setPrefSize(PANE_WIDTH,
                    (allActiveDeposits.size() * DEPOSIT_ROW_HEIGHT) + HEADER_PANE_HEIGHT);
        }

        final int[] counter = {0};
        allActiveDeposits.forEach(deposit -> {
            Pane pane = new Pane();
            pane.setPrefSize(PANE_WIDTH, DEPOSIT_ROW_HEIGHT);
            pane.setLayoutY((counter[0] * DEPOSIT_ROW_HEIGHT) + HEADER_PANE_HEIGHT);
            addComponentToPane(pane, deposit);
            activeList.getChildren().add(pane);
            counter[0] += 1;
        });
    }

    private void addComponentToPane(Pane pane, Deposit deposit) {
        Line separator = new Line(0, 0, PANE_WIDTH, 0);

        Label startDate = new Label(deposit.getStartDate().toLocalDate().toString());
        startDate.setPrefSize(200, 30);
        startDate.setLayoutX(15);
        startDate.setLayoutY(25);
        startDate.setAlignment(Pos.CENTER);

        Label endDate = new Label(deposit.getEndDate().toLocalDate().toString());
        endDate.setPrefSize(200, 30);
        endDate.setLayoutX(245);
        endDate.setLayoutY(25);
        endDate.setAlignment(Pos.CENTER);

        Label amount = new Label(deposit.getDepositAmount().toString());
        amount.setPrefSize(150, 30);
        amount.setLayoutX(475);
        amount.setLayoutY(25);
        amount.setAlignment(Pos.CENTER);

        Label hoursToEnd = new Label(deposit.getHoursToEnd() + "");
        hoursToEnd.setPrefSize(175, 30);
        hoursToEnd.setLayoutX(655);
        hoursToEnd.setLayoutY(25);
        hoursToEnd.setAlignment(Pos.CENTER);

        Label expectedProfit = new Label(deposit.getProfit().setScale(2, RoundingMode.CEILING).toString());
        expectedProfit.setPrefSize(150, 30);
        expectedProfit.setLayoutX(860);
        expectedProfit.setLayoutY(25);
        expectedProfit.setAlignment(Pos.CENTER);

        pane.setStyle("-fx-background-color: #FFFFFF;");
        pane.getChildren().add(separator);
        pane.getChildren().add(startDate);
        pane.getChildren().add(endDate);
        pane.getChildren().add(amount);
        pane.getChildren().add(hoursToEnd);
        pane.getChildren().add(expectedProfit);
    }

    public void setRootController(RootController rootController) {
        this.rootController = rootController;
    }
}
