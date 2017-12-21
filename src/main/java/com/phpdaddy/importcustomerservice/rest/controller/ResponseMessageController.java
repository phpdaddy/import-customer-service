package com.phpdaddy.importcustomerservice.rest.controller;

import com.phpdaddy.importcustomerservice.dao.ResponseMessageRepository;
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
@RequestMapping(path = "/responses", produces = MediaType.APPLICATION_JSON_VALUE)
public class ResponseMessageController {
    @Autowired
    ResponseMessageRepository errorMessageRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Page<?> getCustomers(Pageable pageable) {
        return errorMessageRepository.findAll(pageable);
    }
}
