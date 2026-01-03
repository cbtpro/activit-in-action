//package com.chenbitao.activiti_in_action.activiti_workflow.config;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class DataSourceConfig {
//
//    @Bean(name = "workflowDataSource")
//    @ConfigurationProperties(prefix = "spring.activiti.datasource.workflow")
//    public DataSource workflowDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//}