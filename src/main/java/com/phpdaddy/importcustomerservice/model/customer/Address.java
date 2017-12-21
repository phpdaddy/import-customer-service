package com.phpdaddy.importcustomerservice.model.customer;

import com.univocity.parsers.annotations.Parsed;

public class Address {
    @Parsed(field = "Street")
    private String street;
    private String country;
    @Parsed(field = "ZIP")
    private String zip;
    @Parsed(field = "Place")
    private String city;
    private FederalState federalState;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public FederalState getFederalState() {
        return federalState;
    }

    public void setFederalState(FederalState federalState) {
        this.federalState = federalState;
    }
}
