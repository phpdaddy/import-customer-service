package com.phpdaddy.importcustomerservice;

import com.phpdaddy.importcustomerservice.model.customer.Customer;
import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParserSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CsvConfig {
    @Bean
    public BeanListProcessor<Customer> customerBeanListProcessor() {
        return new BeanListProcessor<>(Customer.class);
    }

    @Bean
    CsvParserSettings csvParserSettings() {
        CsvParserSettings settings = new CsvParserSettings();
        settings.getFormat().setLineSeparator("\n");
        settings.getFormat().setDelimiter('\t');
        settings.setHeaderExtractionEnabled(true);
        settings.setProcessor(customerBeanListProcessor());
        return settings;
    }
}
