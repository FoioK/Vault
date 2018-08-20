package com.wojo.Vault.ControllerFX;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.wojo.Vault.Database.Model.Account;
import com.wojo.Vault.Database.Model.Address;
import com.wojo.Vault.Database.Model.Person;
import com.wojo.Vault.Filters.TextFieldFilter;
import com.wojo.Vault.Service.AccountService;
import com.wojo.Vault.Service.PersonService;
import com.wojo.Vault.Service.impl.AccountServiceImpl;
import com.wojo.Vault.Service.impl.PersonServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javax.swing.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

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
    private JFXTextField city;

    @FXML
    private JFXTextField street;

    @FXML
    private JFXTextField apartmentNumber;

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

        TextFieldFilter.lengthLimiter(city, 40);
        TextFieldFilter.lengthLimiter(street, 40);
        TextFieldFilter.lengthLimiter(apartmentNumber, 40);

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
            createAccount();
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
        if (city.getText().equals("") || street.getText().equals("") || apartmentNumber.getText().equals("")) {
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
        if (!passwordField.getText().equals(repeatPasswordField.getText())) {
            badRepeatPasswordsMessage.setVisible(true);
            isCorrect = false;
        }

        return isCorrect;
    }

    private void createAccount() {
        ResourceBundle bundle = ResourceBundle.getBundle("Bundles.messages");
        String login = personService.createPerson(getPerson(), getAddress());

        if (login.equals("")) {
            JOptionPane.showMessageDialog(null,
                    bundle.getString("AccountCreator.badCreateMessage"),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            String personId = personService.findPersonIdByLogin(login);
            if (!personId.equals("")) {
                accountService.createAccount(new Account(personId, "", BigDecimal.ZERO));
            }

            String message = bundle.getString("AccountCreator.createSuccess");
            JOptionPane.showMessageDialog(null,
                    message + " " + login,
                    "Success",
                    JOptionPane.PLAIN_MESSAGE);

            rootController.loadLoginStep1();
        }
    }

    private Person getPerson() {
        return new Person(
                firstNameField.getText(),
                lastNameField.getText(),
                telephoneNumberField.getText(),
                "",
                passwordField.getText().toCharArray(),
                LocalDateTime.now()
        );
    }

    private Address getAddress() {
        return new Address(
                city.getText(),
                street.getText(),
                apartmentNumber.getText()
        );
    }

    protected void setRootController(RootController rootController) {
        this.rootController = rootController;
    }
}
