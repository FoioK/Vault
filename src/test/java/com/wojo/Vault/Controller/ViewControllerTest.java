package com.wojo.Vault.Controller;

import com.wojo.Vault.Controller.Loader.ViewLoader;
import com.wojo.Vault.Database.DBManager;
import com.wojo.Vault.Database.Model.Person;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@Category(com.wojo.Vault.Categories.GUITest.class)
public class ViewControllerTest extends ApplicationTest {

    private static final String ROOT_VIEW = "Root";

    private static final Integer ID_PERSON = 9801;
    private static final String LOGIN = "IHGFEDCBA";
    private static final String PASSWORD = "TestTest";

    @BeforeClass
    public static void connectionToTestDatabaseAndSetDataToTests() throws IOException, SQLException {
        DBManager.setTestConnectionPath();
        DBManager.dbConnection();

        String updateStatementPerson = "INSERT INTO person " +
                "(idPerson, FIRST_NAME, LAST_NAME, PERSON_ID, ADDRESS, TELEPHONE_NUMBER, EMAIL, LOGIN, PASSWORD) " +
                "VALUES " +
                "(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        assertEquals(1, DBManager.dbExecuteUpdate(updateStatementPerson,
                Arrays.asList(String.valueOf(ID_PERSON), "TestName", "TestName", "01234567899", "Test",
                        "123456789", "Test", LOGIN, PASSWORD)));

        String updateStatementAccounts = "INSERT INTO accounts (idPerson, number, value) VALUES (?, ?, ?)";
        assertEquals(1, DBManager.dbExecuteUpdate(updateStatementAccounts,
                Arrays.asList(String.valueOf(ID_PERSON), "PL4345678901234567980123456", BigDecimal.ZERO.toString())));
    }

    @AfterClass
    public static void clearDatabaseAndDisconnect() throws SQLException {
        String updateStatementPerson = "TRUNCATE TABLE person";
        DBManager.dbExecuteUpdate(updateStatementPerson, null);
        String updateStatementAccounts = "TRUNCATE TABLE accounts";
        DBManager.dbExecuteUpdate(updateStatementAccounts, null);
        DBManager.dbDisconnect();
    }

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = ViewLoader.loadView(this.getClass(), ROOT_VIEW);
        Parent root = ViewLoader.loadPane(loader, 0, 0);
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
        clickOn("#loginField").write(LOGIN);
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
        clickOn("#loginField").write(LOGIN);
        clickOn("#goToNextStep");
        clickOn("#passwordFiled").write(PASSWORD);
        clickOn("#logInButton");
        sleep(50);
        clickOn("#logOut");
    }

    @Test
    public void loadAccountsAndBackTest() {
        clickOn("#loginField").write(LOGIN);
        clickOn("#goToNextStep");
        clickOn("#passwordFiled").write(PASSWORD);
        clickOn("#logInButton");

        clickOn("#accountsLeft");
        sleep(50);
        clickOn("#backToDesktopPane");
        clickOn("#accountsCenter");
        sleep(50);
    }

    @Test
    public void loadPaymentsAndBackTest() {
        clickOn("#loginField").write(LOGIN);
        clickOn("#goToNextStep");
        clickOn("#passwordFiled").write(PASSWORD);
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
        clickOn("#loginField").write(LOGIN);
        clickOn("#goToNextStep");
        clickOn("#passwordFiled").write(PASSWORD);
        clickOn("#logInButton");

        clickOn("#accountsLeft");
        clickOn("#history");
        sleep(50);
        clickOn("#backToAccountsPane");
        sleep(50);
    }

    @Test
    public void loadCashFlowAndBack() {
        clickOn("#loginField").write(LOGIN);
        clickOn("#goToNextStep");
        clickOn("#passwordFiled").write(PASSWORD);
        clickOn("#logInButton");

        clickOn("#cashFlowLeft");
        sleep(50);
        clickOn("#backToDesktopPane");
        clickOn("#cashFlowCenter");
    }
}