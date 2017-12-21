package com.phpdaddy.importcustomerservice.exception;

public class Error {

    private String error;

    public Error(String errorMessage) {
        this.error = errorMessage;
    }
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}