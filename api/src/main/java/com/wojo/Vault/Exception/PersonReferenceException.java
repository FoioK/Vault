package com.wojo.Vault.Exception;

public class PersonReferenceException extends AppException {

    public PersonReferenceException() {
    }

    public PersonReferenceException(String gripe, ErrorCode errorCode) {
        super(gripe, errorCode);
    }
}
