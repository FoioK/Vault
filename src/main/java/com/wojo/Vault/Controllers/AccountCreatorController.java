package com.wojo.Vault.Controllers;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class AccountCreatorController {

	private RootWindowController rootController;

	@FXML
	private JFXButton backToLoginWindow;

	@FXML
	void initialize() {
		backToLoginWindow.addEventHandler(ActionEvent.ACTION, e -> {
			rootController.loadLoginWindowStep1();
		});
	}

	public void setRootController(RootWindowController rootController) {
		this.rootController = rootController;
	}
}
