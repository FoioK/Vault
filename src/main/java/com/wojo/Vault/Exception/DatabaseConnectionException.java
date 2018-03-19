package com.wojo.Vault.Exception;

import java.sql.SQLException;

public class DatabaseConnectionException extends SQLException {

    public DatabaseConnectionException() {
    }

    public DatabaseConnectionException(String gripe) {
        super(gripe);
    }
}
