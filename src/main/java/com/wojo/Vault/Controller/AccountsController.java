package com.wojo.Vault.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ResourceBundle;

public class AccountsController {

    private RootController rootController;
    private DesktopController desktopController;

    @FXML
    private JFXButton exit;

    @FXML
    private JFXButton newTransfer;

    @FXML
    void initialize() {
        addEventHandlers();
    }

    private void addEventHandlers() {
        exit.addEventHandler(ActionEvent.ACTION, e -> {
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
