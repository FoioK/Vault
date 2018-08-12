package com.wojo.Vault.Exception;

public class ExecuteStatementException extends DatabaseException {

    public ExecuteStatementException() {
    }

    public ExecuteStatementException(String gripe, ErrorCode errorCode) {
        super(gripe, errorCode);
    }
}
