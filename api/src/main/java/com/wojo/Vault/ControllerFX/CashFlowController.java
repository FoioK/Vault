package com.wojo.Vault.ControllerFX;

import com.jfoenix.controls.JFXButton;
import com.wojo.Vault.Database.Model.CashFlow;
import com.wojo.Vault.Service.CashFlowService;
import com.wojo.Vault.Service.impl.CashFlowServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class CashFlowController {

    @FXML
    private JFXButton backToDesktopPane;

    @FXML
    private Label firstMonth;

    @FXML
    private Label firstYear;

    @FXML
    private Label firstBalance;

    @FXML
    private Label firstIncomes;

    @FXML
    private Label firstExpenses;

    @FXML
    private Label secondMonth;

    @FXML
    private Label secondYear;

    @FXML
    private Label secondBalance;

    @FXML
    private Label secondIncomes;

    @FXML
    private Label secondExpenses;

    @FXML
    private Label thirdMonth;

    @FXML
    private Label thirdYear;

    @FXML
    private Label thirdBalance;

    @FXML
    private Label thirdIncomes;

    @FXML
    private Label thirdExpenses;

    @FXML
    private Label summaryExpenses;

    @FXML
    private Label summaryBalance;

    @FXML
    private Label summaryIncomes;

    private RootController rootController;

    private CashFlowService cashFlowService = new CashFlowServiceImpl();

    @FXML
    void initialize() {
        addEventHandlers();

        List<CashFlow> cashFlowList =
                cashFlowService.getLastThreeMonthCashFlow(CurrentPerson.getActiveAccount().getAccountId());

        final Integer CURRENT_MONTH_INDEX = 0;
        final Integer A_MONTH_AGO_INDEX = 1;
        final Integer A_TWO_MONTH_AGO_INDEX = 2;

        setLabelsText(getFirstMonthLabels(), cashFlowList.get(CURRENT_MONTH_INDEX));
        setLabelsText(getSecondMonthLabels(), cashFlowList.get(A_MONTH_AGO_INDEX));
        setLabelsText(getThirdMonthLabels(), cashFlowList.get(A_TWO_MONTH_AGO_INDEX));
        setSummaryText(cashFlowList);
    }

    private void addEventHandlers() {
        backToDesktopPane.addEventHandler(ActionEvent.ACTION, event -> rootController.loadDesktopPane());
    }

    private List<Label> getFirstMonthLabels() {
        return Arrays.asList(firstMonth, firstYear, firstBalance, firstIncomes, firstExpenses);
    }

    private List<Label> getSecondMonthLabels() {
        return Arrays.asList(secondMonth, secondYear, secondBalance, secondIncomes, secondExpenses);
    }

    private List<Label> getThirdMonthLabels() {
        return Arrays.asList(thirdMonth, thirdYear, thirdBalance, thirdIncomes, thirdExpenses);
    }

    private void setLabelsText(List<Label> labelsList, CashFlow cashFlow) {
        if (labelsList == null || labelsList.size() != 5) {
            return;
        }
        labelsList.get(0).setText(cashFlow.getMonth().toString());
        labelsList.get(1).setText(cashFlow.getYear().toString());
        labelsList.get(2).setText(cashFlow.getBalance().toString());
        labelsList.get(3).setText(cashFlow.getIncomes().toString());
        labelsList.get(4).setText(cashFlow.getExpenses().toString());
    }

    private void setSummaryText(List<CashFlow> cashFlowList) {
        if (cashFlowList == null || cashFlowList.size() != 3) {
            return;
        }

        BigDecimal sumBalance = BigDecimal.ZERO;
        BigDecimal sumIncomes = BigDecimal.ZERO;
        BigDecimal sumExpenses = BigDecimal.ZERO;

        for (CashFlow cashFlow : cashFlowList) {
            sumBalance = sumBalance.add(cashFlow.getBalance());
            sumIncomes = sumIncomes.add(cashFlow.getIncomes());
            sumExpenses = sumExpenses.add(cashFlow.getExpenses());
        }

        summaryBalance.setText(sumBalance.toString());
        summaryIncomes.setText(sumIncomes.toString());
        summaryExpenses.setText(sumExpenses.toString());
    }

    public void setRootController(RootController rootController) {
        this.rootController = rootController;
    }
}
