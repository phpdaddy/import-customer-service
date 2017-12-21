package com.phpdaddy.importcustomerservice.helper;

import com.phpdaddy.importcustomerservice.model.customer.Customer;
import com.phpdaddy.importcustomerservice.model.customer.Date;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class CustomerHelper {

    public Customer setExtraFields(Customer customer) {
        customer.setUsername(customer.getContact().getEmail().substring(0,
                customer.getContact().getEmail().indexOf("@")));

        customer.setPassword(customer.getUsername());
        Date date = new Date();
        date.setCreatedDate(java.util.Date.from(Instant.now()));
        date.setUpdatedDate(java.util.Date.from(Instant.now()));
        customer.setDate(date);
        return customer;
    }
}
