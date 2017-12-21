FROM maven:3.5.2-jdk-8-alpine
ADD . /import-customer-service

WORKDIR /import-customer-service

RUN mvn clean install -DskipTests;

FROM openjdk:8-jre-alpine
COPY --from=0 /import-customer-service/target/import-customer-service.jar /app/

CMD ["java", "-Xmx200m", "-jar", "/app/import-customer-service.jar"]

EXPOSE 8030
