package com.wojo.Vault.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.wojo.Vault.Service.PersonService;
import com.wojo.Vault.Service.impl.PersonServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LoginStep2Controller {

    private PersonService personService = new PersonServiceImpl();

    private RootController rootController;

    @FXML
    private JFXButton backToStep1;

    @FXML
    private JFXPasswordField passwordFiled;

    @FXML
    private JFXButton logInButton;

    @FXML
    private Label badPasswordMessage;

    @FXML
    private Label badLoginProcessMessage;

    @FXML
    void initialize() {
        setErrorMessages();
        addEventHandlers();
    }

    private void setErrorMessages() {
        badPasswordMessage.setVisible(false);
        badLoginProcessMessage.setVisible(false);
    }

    private void addEventHandlers() {
        backToStep1.addEventFilter(ActionEvent.ACTION, e -> rootController.loadLoginStep1());

        logInButton.addEventHandler(ActionEvent.ACTION, e -> loginProcessStep2());
    }

    private void loginProcessStep2() {
        setErrorMessages();
        if (personService.loginStep2Process(passwordFiled.getText())) {
            loadDesktopPane();
        } else {
            badLoginProcessMessage.setVisible(true);
        }
    }

    private void loadDesktopPane() {
        rootController.loadDesktopPane();
    }

    protected void setRootController(RootController rootController) {
        this.rootController = rootController;
    }

}