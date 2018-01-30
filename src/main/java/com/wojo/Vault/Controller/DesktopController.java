package com.wojo.Vault.Controller;

import com.jfoenix.controls.JFXButton;
import com.wojo.Vault.Database.Model.Person;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ResourceBundle;

public class DesktopController {

    private RootController rootController;

    @FXML
    private JFXButton dashboard;

    @FXML
    private JFXButton logOut;

    @FXML
    private JFXButton accountsLeft;

    @FXML
    private JFXButton paymentsLeft;

    @FXML
    private Label fullName;

    @FXML
    protected AnchorPane mainPane;

    @FXML
    private JFXButton accountsCenter;

    @FXML
    private JFXButton paymentsCenter;

    @FXML
    private JFXButton exit;

    @FXML
    public void initialize() {
        addEventHandlers();
    }

    private void addEventHandlers() {
        dashboard.addEventHandler(ActionEvent.ACTION, e -> {
            rootController.loadDesktopPane();
        });

        accountsLeft.addEventHandler(ActionEvent.ACTION, e -> {
            goToAccounts();
        });

        accountsCenter.addEventHandler(ActionEvent.ACTION, e -> {
            goToAccounts();
        });

        paymentsLeft.addEventHandler(ActionEvent.ACTION, e -> {
            goToPayments();
        });

        paymentsCenter.addEventHandler(ActionEvent.ACTION, e -> {
            goToPayments();
        });

        exit.addEventHandler(ActionEvent.ACTION, e -> {
            exitApplication();
        });

        logOut.addEventHandler(ActionEvent.ACTION, e -> {
            logOutAction();
        });

        fullName.setText(Person.getFirstName() + " " + Person.getLastName());
    }

    private void goToAccounts() {
        mainPane.setVisible(false);
        FXMLLoader loader = new FXMLLoader(
                this.getClass().getResource("/View/Accounts.fxml"));
        ResourceBundle languageBundles = ResourceBundle.getBundle("Bundles.messages");
        loader.setResources(languageBundles);
        AnchorPane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        pane.setLayoutX(375);
        pane.setLayoutY(60);

        AccountsController controller = loader.getController();
        controller.setRootController(rootController);
        controller.setDesktopController(this);
        rootController.addPane(pane);
    }

    protected void goToPayments() {
        mainPane.setVisible(false);
        FXMLLoader loader = new FXMLLoader(
                this.getClass().getResource("/View/Payments.fxml"));
        ResourceBundle languageBundles = ResourceBundle.getBundle("Bundles.messages");
        loader.setResources(languageBundles);
        AnchorPane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        pane.setLayoutX(375);
        pane.setLayoutY(60);

        PaymentsController controller = loader.getController();
        controller.setRootController(rootController);
        rootController.addPane(pane);
    }

    private void exitApplication() {
        Platform.exit();
    }

    private void logOutAction() {
        rootController.loadLoginStep1();
    }

    public void setRootController(RootController rootController) {
        this.rootController = rootController;
    }
}
