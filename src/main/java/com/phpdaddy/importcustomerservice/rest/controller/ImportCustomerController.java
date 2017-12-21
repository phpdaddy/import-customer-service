package com.phpdaddy.importcustomerservice.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phpdaddy.importcustomerservice.dao.CacheRepository;
import com.phpdaddy.importcustomerservice.helper.CacheHelper;
import com.phpdaddy.importcustomerservice.helper.CustomerHelper;
import com.phpdaddy.importcustomerservice.model.Cache;
import com.phpdaddy.importcustomerservice.model.customer.Customer;
import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.ftp.session.FtpRemoteFileTemplate;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RefreshScope
@RestController
@RequestMapping(path = "/",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class ImportCustomerController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Exchange exchange;

    @Autowired
    private FtpRemoteFileTemplate ftpTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BeanListProcessor<Customer> customerBeanListProcessor;

    @Autowired
    private CacheRepository cacheRepository;

    @Autowired
    private CsvParserSettings csvParserSettings;

    @Autowired
    private CustomerHelper customerHelper;

    @Autowired
    private CacheHelper cacheHelper;

    private List<Customer> skipped = new ArrayList<>();
    private List<Customer> queued = new ArrayList<>();

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> importCustomers() {
        log.info("Import has started");

        List<Customer> customers = fetchCustomersFromFtp();

        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (Customer customer : customers) {
            Runnable worker = () -> {
                try {
                    processCustomer(customer);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            };
            executor.execute(worker);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }

        log.info("Import has finished");

        Map<String, Object> response = new HashMap<>();
        response.put("queued", queued);
        response.put("skipped", skipped);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void processCustomer(Customer customer) throws JsonProcessingException {
        log.info("From csv: " + objectMapper.writeValueAsString(customer));
        Customer _customer = customerHelper.setExtraFields(customer);
        if (!saveCache(_customer)) {
            skipped.add(_customer);
            log.info("Customer " + customer.getPid() + " was not modified since last time");
            return;
        }

        queued.add(_customer);
        sendCustomerAsyncToRabbit(_customer);
        log.info("Customer '" + _customer.getPid() + "' was scheduled for import");
    }

    private void sendCustomerAsyncToRabbit(Customer customer) throws JsonProcessingException {
        rabbitTemplate.convertAndSend(
                exchange.getName(),
                "importCustomer.imported",
                objectMapper.writeValueAsString(customer));
    }


    private boolean saveCache(Customer customer) throws JsonProcessingException {
        Cache newCache = cacheHelper.create(customer);
        Cache existingCache = cacheRepository.findByPid(customer.getPid());
        if (existingCache == null || existingCache.getCustomerHash() != newCache.getCustomerHash()) {
            cacheRepository.save(newCache);
            return true;
        } else {
            return false;
        }
    }

    private List<Customer> fetchCustomersFromFtp() {
        final ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        ftpTemplate.get("user_data.csv",
                inputStream -> FileCopyUtils.copy(inputStream, arrayOutputStream));

        CsvParser parser = new CsvParser(csvParserSettings);
        parser.parse(new StringReader(arrayOutputStream.toString()));

        return customerBeanListProcessor.getBeans();
    }
}