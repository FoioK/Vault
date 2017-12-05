package com.wojo.Vault.Controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.wojo.Vault.AccountDAO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class AccountCreatorController {

	private RootWindowController rootController;

    @FXML
    private JFXButton backToLoginWindow;

    @FXML
    private JFXTextField firstNameField;

    @FXML
    private JFXTextField lastNameField;

    @FXML
    private JFXTextField personIdField;

    @FXML
    private JFXTextField adressField;

    @FXML
    private JFXTextField telefonNumberField;
    
    @FXML
    private JFXTextField emailField;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private JFXPasswordField repeatPasswordField;

    @FXML
    private JFXButton createAccount;

	@FXML
	void initialize() {
		backToLoginWindow.addEventHandler(ActionEvent.ACTION, e -> {
			rootController.loadLoginWindowStep1();
		});
		
		createAccount.addEventHandler(ActionEvent.ACTION, e -> {
			String login = AccountDAO.generateLogin();
			List<String> accountDate = getAccountDateList(login);
			try {
				AccountDAO.insertAccount(accountDate);
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			JOptionPane.showMessageDialog(null, "User ID: " + login);
			rootController.loadLoginWindowStep1();
		});
	}

	private List<String> getAccountDateList(String login) {
		List<String> accountDate = new ArrayList<>();
		accountDate.add(firstNameField.getText());
		accountDate.add(lastNameField.getText());
		accountDate.add(personIdField.getText());
		accountDate.add(adressField.getText());
		accountDate.add(telefonNumberField.getText());
		accountDate.add(emailField.getText());
		accountDate.add(login);
		accountDate.add(passwordField.getText());
		
		return accountDate;
	}

	public void setRootController(RootWindowController rootController) {
		this.rootController = rootController;
	}
	
//	public static void addTextLimiter(final JFXTextField tf, final int maxLength) {
//	    tf.textProperty().addListener(new ChangeListener<String>() {
//	        @Override
//	        public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
//	            if (tf.getText().length() > maxLength) {
//	                String s = tf.getText().substring(0, maxLength);
//	                tf.setText(s);
//	            }
//	        }
//	    });
//	}
}
