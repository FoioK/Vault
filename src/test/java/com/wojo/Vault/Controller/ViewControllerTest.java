package com.wojo.Vault.Controller;

import com.wojo.Vault.Database.DBManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.testfx.framework.junit.ApplicationTest;

@Category(com.wojo.Vault.Categories.GUITest.class)
public class ViewControllerTest extends ApplicationTest {

    @BeforeClass
    public static void setTestConnectionPath(){
        DBManager.setTestConnectionPath();
    }

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

    @Test
    public void loadPaymentsHistoryAndBackTest() {
        clickOn("#loginField").write("ABCDEFGHI");
        clickOn("#goToNextStep");
        clickOn("#passwordFiled").write("Test");
        clickOn("#logInButton");

        clickOn("#accountsLeft");
        clickOn("#history");
        sleep(50);
        clickOn("#backToAccountsPane");
        sleep(50);
    }

    @Test
    public void loadCashFlowAndBack() {
        clickOn("#loginField").write("ABCDEFGHI");
        clickOn("#goToNextStep");
        clickOn("#passwordFiled").write("Test");
        clickOn("#logInButton");

        clickOn("#cashFlowLeft");
        sleep(50);
        clickOn("#backToDesktopPane");
        clickOn("#cashFlowCenter");
    }
}