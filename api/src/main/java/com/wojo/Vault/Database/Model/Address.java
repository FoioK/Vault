package com.wojo.Vault.Database.Model;

import java.util.Objects;

public class Address {

    private String addressId;
    private String city;
    private String street;
    private String apartmentNumber;

    public Address(String city, String street, String apartmentNumber) {
        this.city = city;
        this.street = street;
        this.apartmentNumber = apartmentNumber;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;

        return Objects.equals(addressId, address.addressId) &&
                Objects.equals(city, address.city) &&
                Objects.equals(street, address.street) &&
                Objects.equals(apartmentNumber, address.apartmentNumber);
    }

    @Override
    public int hashCode() {

        return Objects.hash(addressId, city, street, apartmentNumber);
    }
}
