package com.wojo.Vault.ControllerFX;

import com.jfoenix.controls.JFXButton;
import com.wojo.Vault.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainController {

    @FXML
    private JFXButton exit;

    @FXML
    void initialize() {
        addEventHandlers();
    }

    private void addEventHandlers() {
        exit.addEventHandler(ActionEvent.ACTION, e -> exitApplication());
    }

    private void exitApplication() {
        Main.exitApplication();
    }
}
