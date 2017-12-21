package com.phpdaddy.importcustomerservice.model.customer;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Date {
    @JsonFormat(pattern = "dd.MM.yyyy")
    private java.util.Date createdDate;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private java.util.Date updatedDate;

    public java.util.Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(java.util.Date createdDate) {
        this.createdDate = createdDate;
    }

    public java.util.Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(java.util.Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}
