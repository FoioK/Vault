package com.wojo.Vault.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ResourceBundle;

public class RootController {

    @FXML
    private AnchorPane root;

    @FXML
    void initialize() {
        loadLoginStep1();
    }

    protected void loadLoginStep1() {
        FXMLLoader loader = new FXMLLoader(
                this.getClass().getResource("/View/LoginStep1.fxml"));
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

        LoginStep1Controller controller = loader.getController();
        controller.setRootController(this);
        setScreen(pane);
    }

    protected  <T extends Node> void setScreen(T pane) {
        root.getChildren().clear();
        loadMainWindow();
        root.getChildren().add(pane);
    }

    protected <T extends Node> void addPane(T pane) {
        root.getChildren().add(pane);
    }

    private boolean loadMainWindow() {
        FXMLLoader loader = new FXMLLoader(
                this.getClass().getResource("/View/Main.fxml"));
        AnchorPane mainPane = null;
        try {
            mainPane = loader.load();
        } catch (IOException e1) {
            e1.printStackTrace();
            return false;
        }
        return root.getChildren().add(mainPane);
    }

    protected void loadDesktopPane() {
        FXMLLoader loader = new FXMLLoader(
                this.getClass().getResource("/View/Desktop.fxml"));
        ResourceBundle languageBundles = ResourceBundle.getBundle("Bundles.messages");
        loader.setResources(languageBundles);
        AnchorPane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DesktopController controller = loader.getController();
        controller.setRootController(this);
        setScreen(pane);
    }
}