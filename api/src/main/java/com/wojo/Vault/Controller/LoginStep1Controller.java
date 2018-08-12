package com.wojo.Vault.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.wojo.Vault.Controller.Loader.ViewLoader;
import com.wojo.Vault.Database.Model.Person;
import com.wojo.Vault.Service.AccountService;
import com.wojo.Vault.Service.PersonService;
import com.wojo.Vault.Service.impl.AccountServiceImpl;
import com.wojo.Vault.Service.impl.PersonServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginStep1Controller {

    private PersonService personService = new PersonServiceImpl();
    private AccountService accountService = new AccountServiceImpl();

    private RootController rootController;
    private ObservableList<String> languageList = FXCollections.observableArrayList("Language", "PL", "EN");

    @FXML
    private JFXButton goToNextStep;

    @FXML
    private JFXTextField loginField;

    @FXML
    private JFXButton openAccountCreator;

    @FXML
    private JFXButton addValue;

    @FXML
    private Label badLoginMessage;

    @FXML
    private ChoiceBox<String> languageBox;

    @FXML
    void initialize() {
        setErrorMessagesVisibleFalse();
        addLanguageBox();
        addEventHandlers();
    }

    private void setErrorMessagesVisibleFalse() {
        badLoginMessage.setVisible(false);
    }

    private void addLanguageBox() {
        languageBox.setItems(languageList);
        languageBox.setValue("Language");
        languageBox.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    languageBox.setValue(newValue);
                    Locale.setDefault(new Locale(newValue));
                    rootController.loadLoginStep1();
                });
    }

    private void addEventHandlers() {
        goToNextStep.addEventHandler(ActionEvent.ACTION, e -> loginProcessStep1());

        openAccountCreator.addEventHandler(ActionEvent.ACTION, e -> loadAccountCreator());

        addValue.addEventHandler(ActionEvent.ACTION, e -> addValueProcess());
    }

    private void loginProcessStep1() {
        setErrorMessagesVisibleFalse();

        String login = loginField.getText();
        final Person person;

        if ((person = personService.findPersonByLogin(login)) != null) {
            loadLoginStep2(person);
        } else {
            badLoginMessage.setVisible(true);
        }
    }

    private static final String LOGIN_STEP2_VIEW = "LoginStep2";
    private static final String ACCOUNT_CREATOR_VIEW = "AccountCreator";

    private void loadLoginStep2(Person person) {
        CurrentPerson.setPerson(person);

        FXMLLoader loader = ViewLoader.loadView(this.getClass(), LOGIN_STEP2_VIEW);
        AnchorPane pane = (AnchorPane) ViewLoader.loadPane(loader, 225, 100);
        LoginStep2Controller controller = loader.getController();
        controller.setRootController(rootController);
        rootController.setScreen(pane);
    }

    private void loadAccountCreator() {
        FXMLLoader loader = ViewLoader.loadView(this.getClass(), ACCOUNT_CREATOR_VIEW);
        AnchorPane pane = (AnchorPane) ViewLoader.loadPane(loader, 225, 100);
        AccountCreatorController controller = loader.getController();
        controller.setRootController(rootController);
        rootController.setScreen(pane);
    }

    private void addValueProcess() {
        ResourceBundle bundle = ResourceBundle.getBundle("Bundles.messages");

        String regex = "[0-9]+";
        String number = JOptionPane.showInputDialog(bundle.getString("LoginStep1.enterNumber"));
        if (!number.matches(regex)) {
            JOptionPane.showMessageDialog(null,
                    bundle.getString("LoginStep1.badNumberMessage"),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        BigDecimal amount;
        try {
            amount = new BigDecimal(JOptionPane.showInputDialog(bundle.getString("LoginStep1.enterAmount")));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    bundle.getString("LoginStep1.badAmountMessage"),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (accountService.addValue(amount, String.valueOf(number))) {
            JOptionPane.showMessageDialog(null,
                    bundle.getString("LoginStep1.addValueSuccess"),
                    "Success", JOptionPane.PLAIN_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null,
                    bundle.getString("LoginStep1.badAddValueMessage"),
                    "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    protected void setRootController(RootController rootController) {
        this.rootController = rootController;
    }
}