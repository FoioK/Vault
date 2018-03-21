package com.wojo.Vault.Database.Model;

public class Address {

    private String addressId;
    private String city;
    private String street;
    private String apartmentNumber;

    public Address(String addressId, String city, String street, String apartmentNumber) {
        this.addressId = addressId;
        this.city = city;
        this.street = street;
        this.apartmentNumber = apartmentNumber;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }
}
