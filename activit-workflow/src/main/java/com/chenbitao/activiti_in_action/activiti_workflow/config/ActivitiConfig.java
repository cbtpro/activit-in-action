package com.chenbitao.activiti_in_action.activiti_workflow.config;

import org.activiti.engine.*;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
public class ActivitiConfig {
    @Bean
    public PlatformTransactionManager workflowTransactionManager(@Qualifier("workflowDataSource") DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }

    @Bean
    public SpringProcessEngineConfiguration processEngineConfiguration(@Qualifier("workflowDataSource") DataSource ds, // 重点：指名道姓
                                                                       @Qualifier("workflowTransactionManager") PlatformTransactionManager tm) {
        SpringProcessEngineConfiguration config = new SpringProcessEngineConfiguration();
        config.setDataSource(ds); // 这样它就绝对拿不到业务库的连接了
        config.setTransactionManager(tm);

        // 1. 设置异步执行器（Activiti 6）
        config.setAsyncExecutorActivate(false);
        config.setDatabaseSchemaUpdate("true");
        config.setHistory("audit");

        // 2. 关键：在此处设置自动部署的资源
        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            // 使用 classpath*: 确保能扫描到多模块下所有 jar 包中的 processes 目录
            Resource[] resources = resolver.getResources("classpath*:processes/*.bpmn20.xml");
            config.setDeploymentResources(resources);
            config.setDeploymentName("auto-deployment");
        } catch (IOException e) {
            // 这里建议记录日志，防止资源加载失败导致流程无法部署
            e.printStackTrace();
        }

        return config;
    }

    @Bean
    public ProcessEngineFactoryBean processEngine(SpringProcessEngineConfiguration config) {
        ProcessEngineFactoryBean factoryBean = new ProcessEngineFactoryBean();
        factoryBean.setProcessEngineConfiguration(config);
        return factoryBean;
    }

    // 暴露 Activiti 的核心服务
    @Bean
    public RepositoryService repositoryService(ProcessEngine processEngine) {
        return processEngine.getRepositoryService();
    }

    @Bean
    public RuntimeService runtimeService(ProcessEngine processEngine) {
        return processEngine.getRuntimeService();
    }

    @Bean
    public TaskService taskService(ProcessEngine processEngine) {
        return processEngine.getTaskService();
    }
}