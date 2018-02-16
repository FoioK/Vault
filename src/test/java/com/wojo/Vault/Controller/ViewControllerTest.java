package com.wojo.Vault.Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

public class ViewControllerTest extends ApplicationTest {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                this.getClass().getResource("/View/Root.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Test
    public void changeLanguageTest() {
        clickOn("#languageBox")
                .moveTo("PL")
                .clickOn(MouseButton.PRIMARY);
        clickOn("#languageBox")
                .moveTo("EN")
                .clickOn(MouseButton.PRIMARY);
        clickOn("#languageBox")
                .moveTo("PL")
                .clickOn(MouseButton.PRIMARY);
        sleep(50);
        clickOn("#languageBox")
                .moveTo("EN")
                .clickOn(MouseButton.PRIMARY);
        sleep(50);
    }

    @Test
    public void backToMainViewFromStepLogin2Test() {
        clickOn("#loginField").write("ABCDEFGHI");
        clickOn("#goToNextStep");
        sleep(50);
        clickOn("#backToStep1");
    }

    @Test
    public void loadAccountCreatorAndBackTest() {
        clickOn("#openAccountCreator");
        sleep(50);
        clickOn("#backToLoginWindow");
    }

    @Test
    public void fullLoginAndLogOutTest() {
        clickOn("#loginField").write("ABCDEFGHI");
        clickOn("#goToNextStep");
        clickOn("#passwordFiled").write("Test");
        clickOn("#logInButton");
        sleep(50);
        clickOn("#logOut");
    }

    @Test
    public void loadAccountsAndBackTest() {
        clickOn("#loginField").write("ABCDEFGHI");
        clickOn("#goToNextStep");
        clickOn("#passwordFiled").write("Test");
        clickOn("#logInButton");

        clickOn("#accountsLeft");
        sleep(50);
        clickOn("#backToDesktopPane");
        clickOn("#accountsCenter");
        sleep(50);
    }

    @Test
    public void loadPaymentsAndBackTest() {
        clickOn("#loginField").write("ABCDEFGHI");
        clickOn("#goToNextStep");
        clickOn("#passwordFiled").write("Test");
        clickOn("#logInButton");

        clickOn("#paymentsLeft");
        sleep(50);
        clickOn("#backToDesktopPane");
        clickOn("#paymentsCenter");
        sleep(50);
        clickOn("#backToDesktopPane");

        clickOn("#accountsLeft");
        clickOn("#newTransfer");
        sleep(50);
    }
}