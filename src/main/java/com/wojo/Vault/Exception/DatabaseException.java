package com.wojo.Vault.Exception;

import java.sql.SQLException;

public class DatabaseException extends SQLException {

    private ErrorCode errorCode;

    DatabaseException() {
    }

    DatabaseException(String gripe, ErrorCode errorCode) {
        super(gripe);
        this.errorCode = errorCode;
    }

    public ErrorCode errorCode() {
        return errorCode;
    }
}
