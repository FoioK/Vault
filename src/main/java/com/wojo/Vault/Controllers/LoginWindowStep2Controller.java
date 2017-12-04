package com.wojo.Vault.Controllers;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class LoginWindowStep2Controller {

	private RootWindowController rootController;

	@FXML
	private JFXButton backToStep1;

	@FXML
	void initialize() {
		backToStep1.addEventFilter(ActionEvent.ACTION, e -> {
			rootController.loadLoginWindowStep1();
		});
	}

	public void setRootController(RootWindowController rootController) {
		this.rootController = rootController;
	}

}