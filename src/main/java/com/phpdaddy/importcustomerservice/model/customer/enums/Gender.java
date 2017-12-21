package com.phpdaddy.importcustomerservice.model.customer.enums;


public enum Gender {
    MALE("M"),
    FEMALE("W"),
    UNDEFINED("U");

    String csvShortCode;

    Gender(String shortCode) {
        this.csvShortCode = shortCode;
    }
}