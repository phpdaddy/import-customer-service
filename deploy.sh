#!/bin/sh

mvn clean install -DskipTests;
cd ../docker; docker-compose up -d --build import-customer-service;