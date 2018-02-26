package com.wojo.Vault.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.wojo.Vault.Filters.TextFieldFilter;
import com.wojo.Vault.Service.AccountService;
import com.wojo.Vault.Service.PersonService;
import com.wojo.Vault.Service.impl.AccountServiceImpl;
import com.wojo.Vault.Service.impl.PersonServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class AccountCreatorController {

    private PersonService personService = new PersonServiceImpl();
    private AccountService accountService = new AccountServiceImpl();
    private RootController rootController;

    @FXML
    private JFXButton backToLoginWindow;

    @FXML
    private JFXTextField firstNameField;

    @FXML
    private Label badFirstNameMessage;

    @FXML
    private JFXTextField lastNameField;

    @FXML
    private Label badLastNameMessage;

    @FXML
    private JFXTextField personIdField;

    @FXML
    private Label badPersonIdMessage;

    @FXML
    private JFXTextField addressField;

    @FXML
    private Label badAddressMessage;

    @FXML
    private JFXTextField telephoneNumberField;

    @FXML
    private Label badTelephoneNumberMessage;

    @FXML
    private JFXTextField emailField;

    @FXML
    private Label badEmailMessage;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private Label badPasswordMessage;

    @FXML
    private JFXPasswordField repeatPasswordField;

    @FXML
    private Label badRepeatPasswordsMessage;

    @FXML
    private JFXButton createAccount;

    @FXML
    void initialize() {
        initTextLimiters();
        setErrorMessages();
        addEventHandlers();
    }

    private void initTextLimiters() {
        TextFieldFilter.lengthLimiter(firstNameField, 25);
        TextFieldFilter.typeString(firstNameField);

        TextFieldFilter.lengthLimiter(lastNameField, 25);
        TextFieldFilter.typeString(lastNameField);

        TextFieldFilter.lengthLimiter(personIdField, 11);
        TextFieldFilter.typeInteger(personIdField);

        TextFieldFilter.lengthLimiter(addressField, 40);

        //TODO split entered text
        TextFieldFilter.lengthLimiter(telephoneNumberField, 9);
        TextFieldFilter.typeInteger(telephoneNumberField);

        TextFieldFilter.lengthLimiter(emailField, 30);

        TextFieldFilter.lengthLimiter(passwordField, 30);

        TextFieldFilter.lengthLimiter(repeatPasswordField, 30);
    }

    private void setErrorMessages() {
        badFirstNameMessage.setVisible(false);
        badLastNameMessage.setVisible(false);
        badPersonIdMessage.setVisible(false);
        badAddressMessage.setVisible(false);
        badTelephoneNumberMessage.setVisible(false);
        badEmailMessage.setVisible(false);
        badPasswordMessage.setVisible(false);
        badRepeatPasswordsMessage.setVisible(false);
    }

    private void addEventHandlers() {
        backToLoginWindow.addEventHandler(ActionEvent.ACTION, e -> rootController.loadLoginStep1());

        createAccount.addEventHandler(ActionEvent.ACTION, e -> createAccountProcess());
    }

    private void createAccountProcess() {
        setErrorMessages();
        if (checkData()) {
            if (isPasswordsEqual()) {
                createAccount();
                return;
            }
            badRepeatPasswordsMessage.setVisible(true);
        }
    }

    private boolean checkData() {
        boolean isCorrect = true;
        if (firstNameField.getText().equals("")) {
            badFirstNameMessage.setVisible(true);
            isCorrect = false;
        }
        if (lastNameField.getText().equals("")) {
            badLastNameMessage.setVisible(true);
            isCorrect = false;
        }
        if (personIdField.getText().equals("")) {
            badPersonIdMessage.setVisible(true);
            isCorrect = false;
        }
        if (addressField.getText().equals("")) {
            badAddressMessage.setVisible(true);
            isCorrect = false;
        }
        if (telephoneNumberField.getText().equals("")) {
            badTelephoneNumberMessage.setVisible(true);
            isCorrect = false;
        }
        if (emailField.getText().equals("")) {
            badEmailMessage.setVisible(true);
            isCorrect = false;
        }
        if (passwordField.getText().equals("")) {
            badPasswordMessage.setVisible(true);
            isCorrect = false;
        }
        return isCorrect;
    }

    private boolean isPasswordsEqual() {
        return passwordField.getText().equals(repeatPasswordField.getText());
    }

    private void createAccount() {
        String login = personService.generateLogin(9);
        List<String> accountData = null;
        if (login != null) {
            accountData = getAccountDataList(login);
        }
        int idPerson = personService.insertPersonToDB(accountData);
        //TODO throw wyjątek -> idPerson < 0 -> błędne dane podczas tworzenia konta
        createAccountNumber(idPerson, "PL", 26);

        JOptionPane.showMessageDialog(null, "User ID: " + login);
        rootController.loadLoginStep1();
    }

    @SuppressWarnings("SameParameterValue")
    private void createAccountNumber(int idPerson, String countryCode, int length) {
        accountService.addNewAccount(idPerson, countryCode, length);
    }

    private List<String> getAccountDataList(String login) {
        List<String> accountData = new ArrayList<>();
        accountData.add(firstNameField.getText());
        accountData.add(lastNameField.getText());
        accountData.add(personIdField.getText());
        accountData.add(addressField.getText());
        accountData.add(telephoneNumberField.getText());
        accountData.add(emailField.getText());
        accountData.add(login);
        accountData.add(passwordField.getText());

        return accountData;
    }

    protected void setRootController(RootController rootController) {
        this.rootController = rootController;
    }
}
