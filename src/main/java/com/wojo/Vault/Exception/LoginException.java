package com.wojo.Vault.Exception;

public class LoginException extends RuntimeException {

    private ErrorCode errorCode;

    public LoginException() {
    }

    public LoginException(String gripe, ErrorCode errorCode) {
        super(gripe);
        this.errorCode = errorCode;
    }

    public ErrorCode errorCode() {
        return errorCode;
    }
}
