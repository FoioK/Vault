package com.wojo.Vault;

import com.wojo.Vault.Controller.Loader.ViewLoader;
import com.wojo.Vault.Database.DBManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;

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
        connectionToDatabase();
        launch(args);
    }

    private static void connectionToDatabase() {
        DBManager.setOriginalConnectionPath();
        try {
            DBManager.dbConnection();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void exitApplication() {
        disconnectionFromDatabase();
        Platform.exit();
    }

    private static void disconnectionFromDatabase() {
        try {
            DBManager.dbDisconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}