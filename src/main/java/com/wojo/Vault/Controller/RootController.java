package com.wojo.Vault.Controller;

import com.wojo.Vault.Controller.Loader.ViewLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

public class RootController {

    private static final String LOGIN_STEP1_VIEW = "LoginStep1";
    private static final String MAIN_VIEW = "Main";
    private static final String DESKTOP_VIEW = "Desktop";

    @FXML
    private AnchorPane root;

    @FXML
    void initialize() {
        loadLoginStep1();
    }

    protected void loadLoginStep1() {
        FXMLLoader loader = ViewLoader.loadView(this.getClass(), LOGIN_STEP1_VIEW);
        AnchorPane pane = (AnchorPane) ViewLoader.loadPane(loader, 225, 100);
        LoginStep1Controller controller = loader.getController();
        controller.setRootController(this);
        setScreen(pane);
    }

    protected <T extends Parent> void setScreen(T pane) {
        root.getChildren().clear();
        loadMainWindow();
        root.getChildren().add(pane);
    }

    private void loadMainWindow() {
        FXMLLoader loader = ViewLoader.loadView(this.getClass(), MAIN_VIEW);
        AnchorPane mainPane = (AnchorPane) ViewLoader.loadPane(loader, 0, 0);
        root.getChildren().add(mainPane);
    }

    protected void loadDesktopPane() {
        FXMLLoader loader = ViewLoader.loadView(this.getClass(), DESKTOP_VIEW);
        AnchorPane pane = (AnchorPane) ViewLoader.loadPane(loader, 0, 0);
        DesktopController controller = loader.getController();
        controller.setRootController(this);
        setScreen(pane);
    }
}