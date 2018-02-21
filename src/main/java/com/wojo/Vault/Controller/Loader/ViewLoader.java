package com.wojo.Vault.Controller.Loader;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ResourceBundle;

public class ViewLoader {

    private static final String BUNDLES_PATH = "Bundles.messages";

    public static FXMLLoader loadView(Class aClass, String viewName) {
        FXMLLoader loader = new FXMLLoader(
                aClass.getClass().getResource("/View/" + viewName + ".fxml"));
        ResourceBundle languageBundles = ResourceBundle.getBundle(BUNDLES_PATH);
        loader.setResources(languageBundles);
        return loader;
    }

    public static Pane loadPane(FXMLLoader loader, int layoutX, int layoutY) {
        Pane pane = null;
        try {
            pane = loader.load();
            pane.setLayoutX(layoutX);
            pane.setLayoutY(layoutY);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return pane;
    }
}
