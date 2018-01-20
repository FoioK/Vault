package com.wojo.Vault.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.wojo.Vault.DAO.AccountDAO;
import com.wojo.Vault.DAO.PersonDAO;
import com.wojo.Vault.Model.Person;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountCreatorController {

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
        initTextLimiter();
        setErrorMessages(false);
        addEventHandlers();
    }

    private void initTextLimiter() {
        addTextLengthLimiter(firstNameField, 25);
        addTextTypeString(firstNameField);

        addTextLengthLimiter(lastNameField, 25);
        addTextTypeString(firstNameField);

        addTextLengthLimiter(personIdField, 11);
        addTextTypeInteger(personIdField);

        addTextLengthLimiter(addressField, 40);

        addTextLengthLimiter(telephoneNumberField, 9);
        addTextTypeInteger(telephoneNumberField);

        addTextLengthLimiter(emailField, 30);
        addTextTypeString(emailField);

        addTextLengthLimiter(passwordField, 30);

        addTextLengthLimiter(repeatPasswordField, 30);
    }

    private static <T extends TextField> void addTextLengthLimiter(final T tf, final int maxLength) {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (tf.getText().length() > maxLength) {
                    String s = tf.getText().substring(0, maxLength);
                    tf.setText(s);
                }
            }
        });
    }

    private static <T extends TextField> void addTextTypeInteger(final T field) {
        field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length() < 1) {
                    field.setText("");
                    return;
                }
                char c = newValue.charAt(newValue.length() - 1);
                if(c < '0' || c > '9') {
                    setText((T) field);
                }
            }
        });
    }

    private static <T extends TextField> void addTextTypeString(final T field) {
        field.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue.length() < 1) {
                    field.setText("");
                    return;
                }
                char c = newValue.charAt(newValue.length() - 1);
                if((c < 'A' || c > 'z') && (c < 'a' || c > 'Z')) {
                    setText((T) field);
                }
            }
        });
    }

    private static <T extends TextField> void setText(T field) {
        try {
            String s = field.getText().substring(0, field.getText().length() - 1);
            field.setText(s);
        } catch (StringIndexOutOfBoundsException e) {
            field.setText("");
            throw e;
        }
    }

    private void setErrorMessages(boolean state) {
        badFirstNameMessage.setVisible(state);
        badLastNameMessage.setVisible(state);
        badPersonIdMessage.setVisible(state);
        badAddressMessage.setVisible(state);
        badTelephoneNumberMessage.setVisible(state);
        badEmailMessage.setVisible(state);
        badPasswordMessage.setVisible(state);
        badRepeatPasswordsMessage.setVisible(state);
    }

    private void addEventHandlers() {
        backToLoginWindow.addEventHandler(ActionEvent.ACTION, e -> {
            rootController.loadLoginStep1();
        });

        createAccount.addEventHandler(ActionEvent.ACTION, e -> {
            createAccountProcess();
        });
    }

    private void createAccountProcess() {
        setErrorMessages(false);
        if(checkData()) {
            if(isPasswordsEqual()) {
                createAccount();
                return;
            }
            badRepeatPasswordsMessage.setVisible(true);
        }
    }

    private boolean checkData() {
        boolean isCorrect = true;
        if(firstNameField.getText().equals("")) {
            badFirstNameMessage.setVisible(true);
            isCorrect = false;
        }
        if(lastNameField.getText().equals("")) {
            badLastNameMessage.setVisible(true);
            isCorrect = false;
        }
        if(personIdField.getText().equals("")) {
            badPersonIdMessage.setVisible(true);
            isCorrect = false;
        }
        if(addressField.getText().equals("")) {
            badAddressMessage.setVisible(true);
            isCorrect = false;
        }
        if(telephoneNumberField.getText().equals("")) {
            badTelephoneNumberMessage.setVisible(true);
            isCorrect = false;
        }
        if(emailField.getText().equals("")) {
            badEmailMessage.setVisible(true);
            isCorrect = false;
        }
        if(passwordField.getText().equals("")) {
            badPasswordMessage.setVisible(true);
            isCorrect = false;
        }
        return isCorrect;
    }

    private boolean isPasswordsEqual() {
        return passwordField.getText().equals(repeatPasswordField.getText());
    }

    private void createAccount() {
        String login = Person.generateLogin(9);
        List<String> accountData = null;
        if(login != null) {
            accountData = getAccountDataList(login);
        }
        try {
            int idPerson = PersonDAO.insertPersonToDB(accountData);
            createAccountNumber(idPerson, "PL", 26);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        JOptionPane.showMessageDialog(null, "User ID: " + login);
        rootController.loadLoginStep1();
    }

    private void createAccountNumber(int idPerson, String countryCode, int length) {
        try {
            AccountDAO.addNewAccount(idPerson, countryCode, length);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    volatile int a = 5;
}
