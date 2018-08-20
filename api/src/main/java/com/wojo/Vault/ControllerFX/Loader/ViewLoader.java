package com.wojo.Vault.ControllerFX.Loader;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ResourceBundle;

public class ViewLoader {

    private static final String BUNDLES_PATH = "Bundles.messages";

    public static FXMLLoader loadView(Class aClass, String viewName) {
        FXMLLoader loader = new FXMLLoader(
                aClass.getResource("/View/" + viewName + ".fxml"));
        loader.setResources(ResourceBundle.getBundle(BUNDLES_PATH));
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
