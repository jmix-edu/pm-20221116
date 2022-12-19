package com.company.pm.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.vault.core.VaultTemplate;

@Configuration
public class DatabaseConfig {

    @Lazy
    @Autowired
    private VaultTemplate vaultTemplate;

    @Primary
    @Profile({"default", "k8s"})
    @Bean("dataSourceProperties")
    @ConfigurationProperties("main.datasource")
    DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Profile("vault")
    @Bean("dataSourceProperties")
    DataSourceProperties dataSourcePropertiesVault() {
        DataSourceProperties properties = new DataSourceProperties();

        DbConfig creds = new ObjectMapper().convertValue(
                vaultTemplate.read("secret/data/application/creds").getData().get("data"),
                DbConfig.class);

        properties.setUrl(creds.getUrl());
        properties.setPassword(creds.getPassword());
        properties.setUsername(creds.getUsername());
        return properties;
    }



}
