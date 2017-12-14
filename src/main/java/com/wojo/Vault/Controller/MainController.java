package com.wojo.Vault.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class MainController {

    @FXML
    private AnchorPane mainPanel;

    @FXML
    private JFXButton exit;

    @FXML
    void initialize() {
        addEventHandlers();
    }

    private void addEventHandlers() {
        exit.addEventHandler(ActionEvent.ACTION, e -> {
            exitApplication();
        });
    }

    private void exitApplication() {
//		Stage stage = (Stage) exit.getScene().getWindow();
//		stage.close();
        Platform.exit();
    }
}
