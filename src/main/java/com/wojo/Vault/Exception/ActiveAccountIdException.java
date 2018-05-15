package com.wojo.Vault.Exception;

public class ActiveAccountIdException extends AppException {

    public ActiveAccountIdException() {
    }

    public ActiveAccountIdException(String gripe, ErrorCode errorCode) {
        super(gripe, errorCode);
    }
}
