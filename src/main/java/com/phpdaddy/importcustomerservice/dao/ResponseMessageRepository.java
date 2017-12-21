package com.phpdaddy.importcustomerservice.dao;

import com.phpdaddy.importcustomerservice.model.message.ResponseMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResponseMessageRepository extends MongoRepository<ResponseMessage, String> {

}
