package com.wojo.Vault.Model;

public class Account {

	private static int idPersonInDatabase;
	private static String firstName;
	private static String lastName;
	private static String personId;
	private static String adress;
	private static String telephonNumber;
	private static String email;
	private static String login;
	private static String password;

	public static int getIdPersonInDatabase() {
		return idPersonInDatabase;
	}

	public static void setIdPersonInDatabase(int idPersonInDatabase) {
		Account.idPersonInDatabase = idPersonInDatabase;
	}

	public static String getFirstName() {
		return firstName;
	}

	public static void setFirstName(String firstName) {
		Account.firstName = firstName;
	}

	public static String getLastName() {
		return lastName;
	}

	public static void setLastName(String lastName) {
		Account.lastName = lastName;
	}

	public static String getPersonId() {
		return personId;
	}

	public static void setPersonId(String personId) {
		Account.personId = personId;
	}

	public static String getAdress() {
		return adress;
	}

	public static void setAdress(String adress) {
		Account.adress = adress;
	}

	public static String getTelephonNumber() {
		return telephonNumber;
	}

	public static void setTelephonNumber(String telephonNumber) {
		Account.telephonNumber = telephonNumber;
	}

	public static String getEmail() {
		return email;
	}

	public static void setEmail(String email) {
		Account.email = email;
	}

	public static String getLogin() {
		return login;
	}

	public static void setLogin(String login) {
		Account.login = login;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		Account.password = password;
	}

}
