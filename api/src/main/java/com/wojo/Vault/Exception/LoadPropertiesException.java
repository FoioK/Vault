package com.wojo.Vault.Exception;

import java.io.IOException;

public class LoadPropertiesException extends IOException {

    private ErrorCode errorCode;

    public LoadPropertiesException() {
    }

    public LoadPropertiesException(String gripe, ErrorCode errorCode) {
        super(gripe);
        this.errorCode = errorCode;
    }

    public ErrorCode errorCode() {
        return errorCode;
    }
}
