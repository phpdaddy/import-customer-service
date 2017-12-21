package com.phpdaddy.importcustomerservice.model.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.phpdaddy.importcustomerservice.model.message.enums.ResponseMessageStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "consumerErrorMessage")
public class ResponseMessage {
    @Id
    private String id;

    private String message;

    private Integer pid;

    private String customer;

    private ResponseMessageStatus status;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private Date date;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public ResponseMessageStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseMessageStatus status) {
        this.status = status;
    }
}
