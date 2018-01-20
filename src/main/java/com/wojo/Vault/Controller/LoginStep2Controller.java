package com.wojo.Vault.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.wojo.Vault.DAO.PersonDAO;
import com.wojo.Vault.Model.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.SQLException;

public class LoginStep2Controller {

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
        setErrorMessages(false);
        addEventHandlers();
    }

    private void setErrorMessages(boolean state) {
        badPasswordMessage.setVisible(false);
        badLoginProcessMessage.setVisible(false);
    }

    private void addEventHandlers() {
        backToStep1.addEventFilter(ActionEvent.ACTION, e -> {
            rootController.loadLoginStep1();
        });

        logInButton.addEventHandler(ActionEvent.ACTION, e -> {
            setErrorMessages(false);
            loginProcessStep2();
        });
    }

    private boolean loginProcessStep2() {
        String[] idPersonAndPassword = null;
        int idPerson = 0;
        String password = null;
        try {
            idPersonAndPassword = PersonDAO.getIdPersonAndPassword(Person.getLogin());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (idPersonAndPassword != null && idPersonAndPassword.length == 2) {
            idPerson = Integer.valueOf(idPersonAndPassword[0]);
            password = idPersonAndPassword[1];
        }
        else {
            badLoginProcessMessage.setVisible(true);
            return false;
        }

        if (password.equals(passwordFiled.getText())) {
            try {
                PersonDAO.insertPersonData(idPerson);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            loadDesktopPane();
            return true;
        } else {
            badPasswordMessage.setVisible(true);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    private void loadDesktopPane() {
        rootController.loadDesktopPane();
    }

    protected void setRootController(RootController rootController) {
        this.rootController = rootController;
    }

}