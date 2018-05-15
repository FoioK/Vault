package com.wojo.Vault.Service.impl;

import com.wojo.Vault.Database.DBManager;
import com.wojo.Vault.Exception.ExecuteStatementException;

public class Configuration {

    public static void connectionToTestDatabase() {
        DBManager.setTestConnectionPath();
        DBManager.dbConnection();
    }

    static void disableForeignKeyCheck() throws ExecuteStatementException {
        final String disableKeyCheck = "SET FOREIGN_KEY_CHECKS = 0";

        DBManager.dbExecuteUpdate(disableKeyCheck, null);
    }

    static void enableForeignKeyCheck() throws ExecuteStatementException {
        final String enableKeyCheck = "SET FOREIGN_KEY_CHECKS = 1";

        DBManager.dbExecuteUpdate(enableKeyCheck, null);
    }

    static void truncateTable(String tableName) throws ExecuteStatementException {
        final String truncateTable = "TRUNCATE TABLE " + tableName;

        DBManager.dbExecuteUpdate(truncateTable, null);
    }
}
