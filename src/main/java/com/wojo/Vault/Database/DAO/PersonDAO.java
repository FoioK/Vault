package com.wojo.Vault.Database.DAO;

import java.util.List;

public interface PersonDAO {

    boolean searchPersonLogin(String login);

    String[] getIdPersonAndPassword(String login);

    void insertPersonData(Integer idPerson);

    int insertPersonToDB(List<String> accountData);

    int getIdPerson(String login);

    <T> boolean deletePerson(T value);
}
