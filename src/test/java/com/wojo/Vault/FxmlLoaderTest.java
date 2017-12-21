package com.wojo.Vault;

import java.io.IOException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class FxmlLoaderTest extends ApplicationTest {

    @Test
    public void rootShouldBeLoad() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                this.getClass().getResource("/View/Root.fxml"));
        AnchorPane root = loader.load();
    }

    @Test
    public void mainShouldBeLoad() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                this.getClass().getResource("/View/Main.fxml"));
        AnchorPane root = loader.load();
    }

    @Test
    public void loginStep1ShouldBeLoad() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                this.getClass().getResource("/View/LoginStep1.fxml"));
        ResourceBundle languageBundles = ResourceBundle.getBundle("Bundles.messages");
        loader.setResources(languageBundles);
        AnchorPane root = loader.load();
    }

    @Test
    public void loginStep2ShouldBeLoad() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                this.getClass().getResource("/View/LoginStep2.fxml"));
        ResourceBundle languageBundles = ResourceBundle.getBundle("Bundles.messages");
        loader.setResources(languageBundles);
        AnchorPane root = loader.load();
    }

    @Test
    public void accountCreatorShouldBeLoad() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                this.getClass().getResource("/View/AccountCreator.fxml"));
        AnchorPane root = loader.load();
    }

    @Test
    public void DesktopLeftPaneShouldBeLoad() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                this.getClass().getResource("/View/DesktopLeftPane.fxml"));
        AnchorPane root = loader.load();
    }
}
