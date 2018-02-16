package com.wojo.Vault;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.util.ResourceBundle;

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
        ResourceBundle languageBundles = ResourceBundle.getBundle("Bundles.messages");
        loader.setResources(languageBundles);
        AnchorPane root = loader.load();
    }

    @Test
    public void desktopShouldBeLoad() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                this.getClass().getResource("/View/Desktop.fxml"));
        ResourceBundle languageBundles = ResourceBundle.getBundle("Bundles.messages");
        loader.setResources(languageBundles);
        AnchorPane root = loader.load();
    }

    @Test
    public void accountsShouldBeLoad() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                this.getClass().getResource("/View/Accounts.fxml"));
        ResourceBundle languageBundles = ResourceBundle.getBundle("Bundles.messages");
        loader.setResources(languageBundles);
        AnchorPane root = loader.load();
    }

    @Test
    public void paymentsShouldBeLoad() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                this.getClass().getResource("/View/Payments.fxml"));
        ResourceBundle languageBundles = ResourceBundle.getBundle("Bundles.messages");
        loader.setResources(languageBundles);
        AnchorPane root = loader.load();
    }
}
