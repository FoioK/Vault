package com.wojo.Vault;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ResourceBundle;

public class Main extends Application {

    private static final String ROOT_PATH = "/View/Root.fxml";

    private static AnchorPane root;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                this.getClass().getResource(ROOT_PATH));
        ResourceBundle languageBundles = ResourceBundle.getBundle("Bundles.messages");
        loader.setResources(languageBundles);
        root = loader.load();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}