package com.wojo.Vault.Controllers;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.wojo.Vault.Account;
import com.wojo.Vault.AccountDAO;
import com.wojo.Vault.DBUtil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class LoginWindowStep2Controller {

	private RootWindowController rootController;

	@FXML
	private JFXButton backToStep1;

	@FXML
	private JFXPasswordField passwordFiled;

	@FXML
	private JFXButton logInButton;

	@FXML
	void initialize() {
		backToStep1.addEventFilter(ActionEvent.ACTION, e -> {
			rootController.loadLoginWindowStep1();
		});

		logInButton.addEventHandler(ActionEvent.ACTION, e -> {
			String queryStatementGetPassword = "SELECT idPerson, PASSWORD FROM person WHERE LOGIN = '"
					+ Account.getLogin() + "';";
			ResultSet resultSet = null;
			try {
				resultSet = DBUtil.dbExecuteQuery(queryStatementGetPassword);
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			int idPerson = 0;
			String password = "";
			try {
				resultSet.next();
				idPerson = resultSet.getInt(1);
				password = resultSet.getString(2);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			if (password.equals(passwordFiled.getText())) {
				AccountDAO.insertPersonDate(idPerson);

				loadAccountPane();
			}
		});
	}

	private void loadAccountPane() {
		FXMLLoader loader = new FXMLLoader(
				this.getClass().getResource("/FXML/DesktopLeftPane.fxml"));
		AnchorPane pane = null;
		try {
			pane = loader.load();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		pane.setLayoutY(60);
		
		DesktopLeftPaneController controller = loader.getController();
		controller.setRootController(rootController);
		rootController.setScreen(pane);
	}

	public void setRootController(RootWindowController rootController) {
		this.rootController = rootController;
	}

}