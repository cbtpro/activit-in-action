package com.chenbitao.activiti_in_action.activiti_business.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.chenbitao.activiti_in_action.activiti_business.mapper", sqlSessionFactoryRef = "bizSqlSessionFactory")
public class MyBatisPlusConfig {

    @Autowired
    @Qualifier("bizDataSource")
    private DataSource bizDataSource;

    @Bean(name = "bizSqlSessionFactory")
    public MybatisSqlSessionFactoryBean sqlSessionFactory() throws Exception {
        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
        factoryBean.setDataSource(bizDataSource);
        // 如果有 xml 路径也需在此手动指定
        // factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        return factoryBean;
    }
}