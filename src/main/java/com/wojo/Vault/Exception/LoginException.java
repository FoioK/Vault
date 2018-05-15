package com.wojo.Vault.Exception;

public class LoginException extends AppException {

    public LoginException() {
    }

    public LoginException(String gripe, ErrorCode errorCode) {
        super(gripe, errorCode);
    }
}
