package com.chenbitao.activiti_in_action.activiti_business.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Primary
    @Bean(name = "bizDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.biz")
    public DataSource bizDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "workflowDataSource")
    @ConfigurationProperties(prefix = "spring.activiti.datasource.workflow")
    public DataSource workflowDataSource() {
        return DataSourceBuilder.create().build();
    }
}