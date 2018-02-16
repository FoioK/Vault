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
    void initialize() {
        addEventHandlers();
    }

    private void addEventHandlers() {
        backToDesktopPane.addEventHandler(ActionEvent.ACTION, e -> {
            rootController.loadDesktopPane();
        });

        newTransfer.addEventHandler(ActionEvent.ACTION, e -> {
            loadPayments();
        });
    }

    private void loadPayments() {
        desktopController.goToPayments();
    }

    public void setRootController(RootController rootController) {
        this.rootController = rootController;
    }

    public void setDesktopController(DesktopController desktopController) {
        this.desktopController = desktopController;
    }
}
