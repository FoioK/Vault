package com.wojo.Vault.Exception;

import java.io.IOException;

public class LoadPropertiesException extends IOException {

    public LoadPropertiesException() {
    }

    public LoadPropertiesException(String gripe) {
        super(gripe);
    }
}
