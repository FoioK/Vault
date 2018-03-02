package com.wojo.Vault.Controller.Loader;

import com.wojo.Vault.Database.DBManager;
import javafx.fxml.FXMLLoader;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.testfx.framework.junit.ApplicationTest;

@Category(com.wojo.Vault.Categories.GUITest.class)
public class ViewLoaderTest extends ApplicationTest {

    private static final String ACCOUNT_CREATOR_VIEW = "AccountCreator";
    private static final String ACCOUNTS_VIEW = "Accounts";
    private static final String CASH_FLOW_VIEW = "CashFlow";
    private static final String DESKTOP_VIEW = "Desktop";
    private static final String LOGIN_STEP_1_VIEW = "LoginStep1";
    private static final String LOGIN_STEP_2_VIEW = "LoginStep2";
    private static final String MAIN_VIEW = "Main";
    private static final String PAYMENTS_VIEW = "Payments";
    private static final String PAYMENTS_HISTORY_VIEW = "PaymentsHistory";
    private static final String ROOT_VIEW = "Root";

    @BeforeClass
    public static void setTestConnectionPath(){
        DBManager.setTestConnectionPath();
    }

    @Test
    public void accountCreatorShouldBeLoad() {
        FXMLLoader loader = ViewLoader.loadView(this.getClass(), ACCOUNT_CREATOR_VIEW);
        ViewLoader.loadPane(loader, 0, 0);
    }

    @Test
    public void accountsShouldBeLoad() {
        FXMLLoader loader = ViewLoader.loadView(this.getClass(), ACCOUNTS_VIEW);
        ViewLoader.loadPane(loader, 0, 0);
    }

    @Test
    public void cashFlowShouldBeLoad() {
        FXMLLoader loader = ViewLoader.loadView(this.getClass(), CASH_FLOW_VIEW);
        ViewLoader.loadPane(loader, 0, 0);
    }

    @Test
    public void desktopShouldBeLoad() {
        FXMLLoader loader = ViewLoader.loadView(this.getClass(), DESKTOP_VIEW);
        ViewLoader.loadPane(loader, 0, 0);
    }

    @Test
    public void loginStep1ShouldBeLoad() {
        FXMLLoader loader = ViewLoader.loadView(this.getClass(), LOGIN_STEP_1_VIEW);
        ViewLoader.loadPane(loader, 0, 0);
    }

    @Test
    public void loginStep2ShouldBeLoad() {
        FXMLLoader loader = ViewLoader.loadView(this.getClass(), LOGIN_STEP_2_VIEW);
        ViewLoader.loadPane(loader, 0, 0);
    }

    @Test
    public void mainShouldBeLoad() {
        FXMLLoader loader = ViewLoader.loadView(this.getClass(), MAIN_VIEW);
        ViewLoader.loadPane(loader, 0, 0);
    }

    @Test
    public void paymentsShouldBeLoad() {
        FXMLLoader loader = ViewLoader.loadView(this.getClass(), PAYMENTS_VIEW);
        ViewLoader.loadPane(loader, 0, 0);
    }

    @Test
    public void paymentsHistoryShouldBeLoad() {
        FXMLLoader loader = ViewLoader.loadView(this.getClass(), PAYMENTS_HISTORY_VIEW);
        ViewLoader.loadPane(loader, 0, 0);
    }

    @Test
    public void rootShouldBeLoad() {
        FXMLLoader loader = ViewLoader.loadView(this.getClass(), ROOT_VIEW);
        ViewLoader.loadPane(loader, 0, 0);
    }
}
