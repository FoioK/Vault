package com.wojo.Vault.Database.DAO;

import java.util.List;

public interface PersonDAO {

    boolean searchPersonLogin(String login);

    String[] getIdPersonAndPassword(String login);

    boolean insertPersonData(Integer idPerson);

    int insertPersonToDB(List<String> accountData);

    <T> boolean deletePerson(T value);
}
