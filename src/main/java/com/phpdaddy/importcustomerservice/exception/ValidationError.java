package com.phpdaddy.importcustomerservice.exception;

import java.util.HashMap;
import java.util.Map;

public class ValidationError extends com.phpdaddy.importcustomerservice.exception.Error {

    private Map<String, String> data;

    public ValidationError(Map<String, String> data) {
        super("Validation Error");
        this.data = data;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(HashMap<String, String> data) {
        this.data = data;
    }
}