package com.wojo.Vault.Model;

public class Person {

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
		Person.idPersonInDatabase = idPersonInDatabase;
	}

	public static String getFirstName() {
		return firstName;
	}

	public static void setFirstName(String firstName) {
		Person.firstName = firstName;
	}

	public static String getLastName() {
		return lastName;
	}

	public static void setLastName(String lastName) {
		Person.lastName = lastName;
	}

	public static String getPersonId() {
		return personId;
	}

	public static void setPersonId(String personId) {
		Person.personId = personId;
	}

	public static String getAdress() {
		return adress;
	}

	public static void setAdress(String adress) {
		Person.adress = adress;
	}

	public static String getTelephonNumber() {
		return telephonNumber;
	}

	public static void setTelephonNumber(String telephonNumber) {
		Person.telephonNumber = telephonNumber;
	}

	public static String getEmail() {
		return email;
	}

	public static void setEmail(String email) {
		Person.email = email;
	}

	public static String getLogin() {
		return login;
	}

	public static void setLogin(String login) {
		Person.login = login;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		Person.password = password;
	}

}
