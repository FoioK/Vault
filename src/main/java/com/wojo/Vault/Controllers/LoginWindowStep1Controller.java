package com.wojo.Vault.Controllers;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class LoginWindowStep1Controller {

	private RootWindowController rootController;

	@FXML
	private JFXButton goToNextStep;

	@FXML
	private JFXButton openAccountCreator;

	@FXML
	void initialize() {
		goToNextStep.addEventHandler(ActionEvent.ACTION, e -> {
			loadLoginWindowStep2();
		});
		
		openAccountCreator.addEventHandler(ActionEvent.ACTION, e -> {
			loadAccountCreator();
		});
	}


	private void loadLoginWindowStep2() {
		FXMLLoader loader = new FXMLLoader(
				this.getClass().getResource("/FXML/LoginWindowStep2.fxml"));
		AnchorPane pane = null;
		try {
			pane = loader.load();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		pane.setLayoutX(225);
		pane.setLayoutY(100);

		LoginWindowStep2Controller controller = loader.getController();
		controller.setRootController(rootController);
		rootController.setScreen(pane);
	}

	private void loadAccountCreator() {
		FXMLLoader loader = new FXMLLoader(
				this.getClass().getResource("/FXML/AccountCreator.fxml"));
		AnchorPane pane = null;
		try {
			pane = loader.load();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		pane.setLayoutX(225);
		pane.setLayoutY(100);

		AccountCreatorController controller = loader.getController();
		controller.setRootController(rootController);
		rootController.setScreen(pane);
	}

	public void setRootController(RootWindowController rootController) {
		this.rootController = rootController;
	}

}