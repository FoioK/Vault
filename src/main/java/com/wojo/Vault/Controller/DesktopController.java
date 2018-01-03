package com.wojo.Vault.Controller;

import com.jfoenix.controls.JFXButton;
import com.wojo.Vault.Model.Account;
import com.wojo.Vault.Model.Person;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DesktopController {

    private RootController rootController;

    @FXML
    private JFXButton exit;

    @FXML
    private JFXButton logOut;

    @FXML
    private Label fullName;

    @FXML
    public void initialize() {
        addEventHandlers();
    }

    private void addEventHandlers() {
        exit.addEventHandler(ActionEvent.ACTION, e -> {
            exitApplication();
        });

        logOut.addEventHandler(ActionEvent.ACTION, e-> {
            logOutAction();
        });

        fullName.setText(Person.getFirstName() + " " + Person.getLastName());
    }

    private void exitApplication() {
        Platform.exit();
    }

    private void logOutAction() {
        rootController.loadLoginStep1();
    }

    public void setRootController(RootController rootController) {
        this.rootController = rootController;
    }
}
