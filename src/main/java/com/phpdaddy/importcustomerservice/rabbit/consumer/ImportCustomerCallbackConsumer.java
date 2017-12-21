package com.phpdaddy.importcustomerservice.rabbit.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.phpdaddy.importcustomerservice.dao.ResponseMessageRepository;
import com.phpdaddy.importcustomerservice.model.message.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ImportCustomerCallbackConsumer {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ResponseMessageRepository responseMessageRepository;

    @RabbitListener(queues = "importCustomer.imported.callback")
    public void handle(String messageJson) throws IOException {
        try {
            ResponseMessage message = objectMapper.readValue(messageJson, ResponseMessage.class);

            log.info("Response from consumer: " + messageJson);

            responseMessageRepository.save(message);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new AmqpRejectAndDontRequeueException(ex);
        }
    }
}
