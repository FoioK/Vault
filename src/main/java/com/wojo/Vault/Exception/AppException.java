package com.wojo.Vault.Exception;

public class AppException extends RuntimeException {

    private ErrorCode errorCode;

    AppException() {
    }

    public AppException(String gripe, ErrorCode errorCode) {
        super(gripe);
        this.errorCode = errorCode;
    }

    public ErrorCode errorCode() {
        return errorCode;
    }
}
