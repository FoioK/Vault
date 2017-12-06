package com.wojo.Vault.Controllers;

import java.io.IOException;
import java.sql.SQLException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.wojo.Vault.Account;
import com.wojo.Vault.AccountDAO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class LoginWindowStep1Controller {

	private RootWindowController rootController;

	@FXML
	private JFXButton goToNextStep;
	
	@FXML
	private JFXTextField loginField;

	@FXML
	private JFXButton openAccountCreator;

	@FXML
	void initialize() {
		goToNextStep.addEventHandler(ActionEvent.ACTION, e -> {
			boolean isLogin = false;
			try {
				isLogin = AccountDAO.searchPersonLogin(loginField.getText());
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			if(isLogin) {
				Account.setLogin(loginField.getText());
				loadLoginWindowStep2();
			}
			else {
				//TODO badLogin
			}
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