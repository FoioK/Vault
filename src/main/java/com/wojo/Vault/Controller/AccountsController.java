package com.wojo.Vault.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class AccountsController {

    private RootController rootController;
    private DesktopController desktopController;

    @FXML
    private JFXButton backToDesktopPane;

    @FXML
    private JFXButton newTransfer;

    @FXML
    private JFXButton history;


    @FXML
    void initialize() {
        addEventHandlers();
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

    public void setDesktopController(DesktopController desktopController) {
        this.desktopController = desktopController;
    }
}
