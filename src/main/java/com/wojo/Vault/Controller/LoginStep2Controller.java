package com.wojo.Vault.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.wojo.Vault.DAO.PersonDAO;
import com.wojo.Vault.Model.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

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
    void initialize() {
        addEventHandlers();
    }

    private void addEventHandlers() {
        backToStep1.addEventFilter(ActionEvent.ACTION, e -> {
            rootController.loadLoginStep1();
        });

        logInButton.addEventHandler(ActionEvent.ACTION, e -> {
            loginProcessStep2();
        });
    }

    private boolean loginProcessStep2() {
        String[] idPersonAndPassword = null;
        int idPerson;
        String password;
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
            return false;
            //TODO error login proces
        }

        if (password.equals(passwordFiled.getText())) {
            try {
                PersonDAO.insertPersonDate(idPerson);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            loadDesktopPane();
            return true;
        } else {
            //TODO badPassword
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