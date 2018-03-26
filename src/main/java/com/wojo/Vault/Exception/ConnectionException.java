package com.wojo.Vault.Exception;

public class ConnectionException extends DatabaseException {

    public ConnectionException() {
    }

    public ConnectionException(String gripe, ErrorCode errorCode) {
        super(gripe, errorCode);
    }
}
