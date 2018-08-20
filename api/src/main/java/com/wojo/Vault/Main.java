package com.wojo.Vault;

import com.wojo.Vault.ControllerFX.Loader.ViewLoader;
import com.wojo.Vault.Database.DBManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    private static final String ROOT_VIEW = "Root";

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = ViewLoader.loadView(this.getClass(), ROOT_VIEW);
        Parent root = ViewLoader.loadPane(loader, 0, 0);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
        connectionToDatabase();
    }

    private static void connectionToDatabase() {
        DBManager.setOriginalConnectionPath();
        DBManager.dbConnection();
    }

    public static void exitApplication() {
        disconnectionFromDatabase();
        Platform.exit();
    }

    private static void disconnectionFromDatabase() {
        DBManager.dbDisconnect();
    }
}