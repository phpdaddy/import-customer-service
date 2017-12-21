package com.phpdaddy.importcustomerservice.model.customer;


import com.univocity.parsers.annotations.Parsed;

public class Contact {
    @Parsed(field = "email")
    private String email;
    private String mobile;
    private String phone;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
