package com.phpdaddy.importcustomerservice.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phpdaddy.importcustomerservice.model.Cache;
import com.phpdaddy.importcustomerservice.model.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;

@Service
public class CacheHelper {
    @Autowired
    private ObjectMapper objectMapper;

    public Cache create(Customer customer) throws JsonProcessingException {
        Cache cache = new Cache();
        cache.setCustomerHash(Objects.hash(objectMapper.writeValueAsString(customer)));
        cache.setPid(customer.getPid());
        cache.setDate(java.util.Date.from(Instant.now()));
        return cache;
    }
}
