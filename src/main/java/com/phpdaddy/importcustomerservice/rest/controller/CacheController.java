package com.phpdaddy.importcustomerservice.rest.controller;

import com.phpdaddy.importcustomerservice.dao.CacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequestMapping(path = "/cache", produces = MediaType.APPLICATION_JSON_VALUE)
public class CacheController {
    @Autowired
    CacheRepository cacheRepository;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public Page<?> getCustomers(Pageable pageable) {
        return cacheRepository.findAll(pageable);
    }
}
