package com.phpdaddy.importcustomerservice;

import com.phpdaddy.importcustomerservice.config.FtpServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import org.springframework.integration.ftp.session.FtpRemoteFileTemplate;

@Configuration
public class FtpConfig {

    @Autowired
    FtpServerProperties ftpServerProperties;

    @Bean
    public DefaultFtpSessionFactory ftpSessionFactory() {
        DefaultFtpSessionFactory sf = new DefaultFtpSessionFactory();
        if (ftpServerProperties.getServer() != null) {
            sf.setHost(ftpServerProperties.getServer());
        }
        if (ftpServerProperties.getPort() != null) {
            sf.setPort(ftpServerProperties.getPort());
        }
        if (ftpServerProperties.getUsername() != null) {
            sf.setUsername(ftpServerProperties.getUsername());
        }
        if (ftpServerProperties.getUsername() != null) {
            sf.setPassword(ftpServerProperties.getPassword());
        }
        sf.setClientMode(2);
        return sf;
    }

    @Bean
    public FtpRemoteFileTemplate template() {
        return new FtpRemoteFileTemplate(ftpSessionFactory());
    }

}
