package com.wojo.Vault.Controllers;

import com.jfoenix.controls.JFXButton;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class MainWindowController {

	@FXML
	private AnchorPane mainPanel;

	@FXML
	private JFXButton exit;

	@FXML
	void initialize() {
		exit.addEventHandler(ActionEvent.ACTION, e -> {
			Platform.exit();
		});
	}
}
