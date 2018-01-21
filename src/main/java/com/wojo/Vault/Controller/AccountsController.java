package com.wojo.Vault.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class AccountsController {

    private RootController rootController;

    @FXML
    private JFXButton exit;

    @FXML
    void initialize() {
        addEventHandlers();
    }

    private void addEventHandlers() {
        exit.addEventHandler(ActionEvent.ACTION, e -> {
            rootController.loadDesktopPane();
        });
    }

    public void setRootController(RootController rootController) {
        this.rootController = rootController;
    }
}
