package com.wojo.Vault.Controller;

import com.wojo.Vault.DAO.PersonDAO;
import com.wojo.Vault.Util.DBUtil;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.AfterClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class ViewControllerTest extends ApplicationTest {

    private static final String ROOT_PATH = "/View/Root.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                this.getClass().getResource(ROOT_PATH));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @AfterClass
    public static void deleteCreatedAccounts() throws SQLException {
        String queryStatement = "SELECT idPerson FROM person " +
                "WHERE FIRST_NAME LIKE ? AND LAST_NAME LIKE ? AND ADDRESS LIKE ?";
        ResultSet resultSet = DBUtil.dbExecuteQuery(queryStatement,
                Arrays.asList(new String[]{"ToDelete", "ToDelete", "ToDelete"}));
        while (resultSet.next()) {
            System.out.println(resultSet.getInt("idPerson"));
            PersonDAO.deletePerson(Integer.valueOf(resultSet.getInt("idPerson")));
        }
    }

    @Test
    public void loadNextStepLoginTest() {
        clickOn("#loginField").write("ABCDEFGHI");
        clickOn("#goToNextStep");
    }

    @Test
    public void backToMainViewFromStepLogin2Test() {
        clickOn("#loginField").write("ABCDEFGHI");
        clickOn("#goToNextStep");
        sleep(100);
        clickOn("#backToStep1");
    }

    @Test
    public void loadAccountCreatorTest() {
        clickOn("#openAccountCreator");
    }

    @Test
    public void backToMainViewFromAccountCreatorTest() {
        clickOn("#openAccountCreator");
        sleep(100);
        clickOn("#backToLoginWindow");
    }

    @Test
    public void fullLoginTest() {
        clickOn("#loginField").write("ABCDEFGHI");
        clickOn("#goToNextStep");
        clickOn("#passwordFiled").write("Test");
        clickOn("#logInButton");
        sleep(200);
    }

    @Test
    public void logOutTest() {
        clickOn("#loginField").write("ABCDEFGHI");
        clickOn("#goToNextStep");
        clickOn("#passwordFiled").write("Test");
        clickOn("#logInButton");
        sleep(100);
        clickOn("#logOut");
    }

    @Test
    public void createAccountTest() {
        clickOn("#openAccountCreator");
        clickOn("#firstNameField").write("ToDelete");
        clickOn("#lastNameField").write("ToDelete");
        clickOn("#personIdField").write("ToDelete");
        clickOn("#addressField").write("ToDelete");
        clickOn("#telephoneNumberField").write("ToDelete");
        clickOn("#emailField").write("ToDelete");
        clickOn("#passwordField").write("ToDelete");
        clickOn("#repeatPasswordField").write("ToDelete");
        clickOn("#createAccount");
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
        clickOn("#languageBox")
                .moveTo("EN")
                .clickOn(MouseButton.PRIMARY);
    }

}