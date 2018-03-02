package com.wojo.Vault.Service;

import java.util.List;

public interface PersonService {
    boolean searchPersonLogin(String login);

    void setPeronLogin(String login);

    boolean loginStep2Process(String password);

    int insertPersonToDB(List<String> accountDate);

    String generateLogin(int length);
}
