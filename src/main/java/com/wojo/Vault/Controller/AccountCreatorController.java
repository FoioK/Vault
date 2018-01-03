package com.wojo.Vault.Controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.wojo.Vault.DAO.AccountDAO;
import com.wojo.Vault.DAO.PersonDAO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class AccountCreatorController {

	private RootController rootController;

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
		addEventHandlers();
	}

	private void addEventHandlers() {
		backToLoginWindow.addEventHandler(ActionEvent.ACTION, e -> {
			rootController.loadLoginStep1();
		});
		
		createAccount.addEventHandler(ActionEvent.ACTION, e -> {
			createAccountProces();
			//TODO repeatPassword
		});
	}

	private void createAccountProces() {
		String login = PersonDAO.generateLogin(9);
		List<String> accountDate = getAccountDateList(login);
		try {
			int idPerson = PersonDAO.insertPersonToDB(accountDate);
			createAccountNumber(idPerson, "PL", 26);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		JOptionPane.showMessageDialog(null, "User ID: " + login);
		rootController.loadLoginStep1();
	}

	private void createAccountNumber(int idPerson, String countryCode, int length) {
		AccountDAO.addNewAccount(idPerson, countryCode, length);
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

	public void setRootController(RootController rootController) {
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
