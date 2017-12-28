package com.wojo.Vault.Controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.wojo.Vault.DAO.PersonDAO;
import com.wojo.Vault.Model.Person;
import com.wojo.Vault.Util.DBUtil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

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

    private void loginProcessStep2() {
        ResultSet resultSet = getIdPersonAndPassword();
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
            PersonDAO.insertPersonDate(idPerson);
            loadDesktopPane();
        } else {
            //TODO badPassword
        }
    }

    private ResultSet getIdPersonAndPassword() {
        String queryStatementGetPassword = "SELECT idPerson, PASSWORD FROM person WHERE LOGIN = '"
                + Person.getLogin() + "';";
        ResultSet resultSet = null;
        try {
            resultSet = DBUtil.dbExecuteQuery(queryStatementGetPassword);
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return resultSet;
    }

    private void loadDesktopPane() {
        rootController.loadDesktopPane();
    }

    public void setRootController(RootController rootController) {
        this.rootController = rootController;
    }

}