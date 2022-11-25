package com.company.pm;

import io.jmix.autoconfigure.data.JmixLiquibaseCreator;
import io.jmix.data.impl.liquibase.LiquibaseChangeLogProcessor;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import io.jmix.core.JmixModules;
import io.jmix.core.Resources;
import io.jmix.data.impl.JmixEntityManagerFactoryBean;
import io.jmix.data.impl.JmixTransactionManager;
import io.jmix.data.persistence.DbmsSpecifics;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
public class TsStoreConfiguration {

    @Bean
    @ConfigurationProperties("ts.datasource")
    DataSourceProperties tsDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "ts.datasource.hikari")
    DataSource tsDataSource(@Qualifier("tsDataSourceProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean
    LocalContainerEntityManagerFactoryBean tsEntityManagerFactory(
            @Qualifier("tsDataSource") DataSource dataSource,
            JpaVendorAdapter jpaVendorAdapter,
            DbmsSpecifics dbmsSpecifics,
            JmixModules jmixModules,
            Resources resources) {
        return new JmixEntityManagerFactoryBean("ts", dataSource, jpaVendorAdapter, dbmsSpecifics, jmixModules, resources);
    }

    @Bean
    JpaTransactionManager tsTransactionManager(@Qualifier("tsEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JmixTransactionManager("ts", entityManagerFactory);
    }

    @Bean
    public SpringLiquibase tsLiquibase(
            LiquibaseChangeLogProcessor processor,
            @Qualifier("tsDataSource") DataSource dataSource) {
        return JmixLiquibaseCreator.create(dataSource, new LiquibaseProperties(), processor, "ts");
    }
}
