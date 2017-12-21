package com.phpdaddy.importcustomerservice.dao;

import com.phpdaddy.importcustomerservice.model.Cache;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CacheRepository extends MongoRepository<Cache, String> {
    Cache findByPid(Integer pid);
}
