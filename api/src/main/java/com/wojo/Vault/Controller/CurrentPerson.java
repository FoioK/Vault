package com.wojo.Vault.Controller;

import com.wojo.Vault.Database.Model.Account;
import com.wojo.Vault.Database.Model.Person;
import com.wojo.Vault.Exception.ActiveAccountIdException;
import com.wojo.Vault.Exception.ErrorCode;
import com.wojo.Vault.Exception.PersonReferenceException;

class CurrentPerson {

    private static Person person;
    private static Integer activeAccountIndex = 0;

    static Person getInstance() {
        return person;
    }

    static void setPerson(Person person) {
        CurrentPerson.person = person;
    }

    static Account getActiveAccount() {
        if (person == null) {
            throw new PersonReferenceException("null person reference: get active account",
                    ErrorCode.NO_PERSON_REFERENCE);
        }
        if (person.getAccountList().size() < activeAccountIndex) {
            throw new ActiveAccountIdException("account list is less than active account index",
                    ErrorCode.NO_ACCOUNT_FOR_INDEX);
        }

        return person.getAccountList().get(activeAccountIndex);
    }

    public static void setActiveAccountIndex(Integer activeAccountIndex) {
        CurrentPerson.activeAccountIndex = activeAccountIndex;
    }
}
