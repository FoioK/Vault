package com.wojo.Vault.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.wojo.Vault.DAO.PersonDAO;
import com.wojo.Vault.Model.Person;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginStep1Controller {

    private RootController rootController;
    private ObservableList<String> languageList = FXCollections.observableArrayList("Language", "PL", "EN");

    @FXML
    private JFXButton goToNextStep;

    @FXML
    private JFXTextField loginField;

    @FXML
    private JFXButton openAccountCreator;

    @FXML
    private ChoiceBox<String> languageBox;

    @FXML
    void initialize() {
        addLanguageBox();
        addEventHandlers();
    }

    private void addLanguageBox() {
        languageBox.setItems(languageList);
        languageBox.setValue("Language");
        languageBox.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                languageBox.setValue(newValue);
                Locale.setDefault(new Locale(newValue));
                rootController.loadLoginStep1();
            }
        });
    }


    private void addEventHandlers() {
        goToNextStep.addEventHandler(ActionEvent.ACTION, e -> {
            loginProcessStep1();
        });

        openAccountCreator.addEventHandler(ActionEvent.ACTION, e -> {
            loadAccountCreator();
        });
    }

    private void loginProcessStep1() {
        if (isLoginExist()) {
            Person.setLogin(loginField.getText());
            loadLoginStep2();
        } else {
            //TODO badLogin
        }
    }

    private boolean isLoginExist() {
        boolean isLogin = false;
        try {
            isLogin = PersonDAO.searchPersonLogin(loginField.getText());
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return isLogin;
    }


    private void loadLoginStep2() {
        FXMLLoader loader = new FXMLLoader(
                this.getClass().getResource("/View/LoginStep2.fxml"));
        ResourceBundle languageBundles = ResourceBundle.getBundle("Bundles.messages");
        loader.setResources(languageBundles);
        AnchorPane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        pane.setLayoutX(225);
        pane.setLayoutY(100);

        LoginStep2Controller controller = loader.getController();
        controller.setRootController(rootController);
        rootController.setScreen(pane);
    }

    private void loadAccountCreator() {
        FXMLLoader loader = new FXMLLoader(
                this.getClass().getResource("/View/AccountCreator.fxml"));
        ResourceBundle languageBundles = ResourceBundle.getBundle("Bundles.messages");
        loader.setResources(languageBundles);
        AnchorPane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        pane.setLayoutX(225);
        pane.setLayoutY(100);

        AccountCreatorController controller = loader.getController();
        controller.setRootController(rootController);
        rootController.setScreen(pane);
    }

    public void setRootController(RootController rootController) {
        this.rootController = rootController;
    }

}