package com.chenbitao.activiti_in_action.activiti_business.config;

//import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
@Component
@Profile({"dev", "test"})
// ⭐️ 实现 ApplicationRunner 接口
public class DatabaseInitializer implements ApplicationRunner {

    @Autowired
    // 注入 DynamicRoutingDataSource，它是所有数据源的容器
    @Qualifier("bizDataSource")
    private DataSource bizDataSource;

    private static final String PRIMARY_DATASOURCE_KEY = "primary";
    
    // ⭐️ 移除 @PostConstruct，将初始化逻辑移至 run 方法
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("▶️ Starting database initialization (via ApplicationRunner)...");

        DataSource primaryDataSource = bizDataSource;

        if (primaryDataSource == null) {
            log.warn("⚠️ Cannot find primary datasource named '" + PRIMARY_DATASOURCE_KEY + 
                     "'. Please check dynamic datasource configuration. Skipping initialization.");
            return;
        }

        try (Connection conn = primaryDataSource.getConnection()) {// 打印当前连接的数据库元数据，看看它到底连到哪去了
            log.info("📊 正在初始化数据库。当前连接 URL: {}", conn.getMetaData().getURL());
            log.info("📊 当前登录用户: {}", conn.getMetaData().getUserName());
            initDatabase(conn);
        } catch (SQLException e) {
            log.error("❌ 数据库连接失败！请检查 URL 和账号密码。", e);
            log.error("❌ Failed to establish primary database connection.", e);
            throw new RuntimeException("Primary database connection failed.", e);
        }
    }

    /**
     * 执行 SQL 脚本
     */
    private void initDatabase(Connection conn) {
        try {
            // 1. 执行建表脚本
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("db/schema.sql"));
            
            // 2. 执行初始化数据脚本
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("db/data.sql"));
            
            log.info("✅ Primary database schema and data initialized successfully.");
        } catch (Exception e) {
            log.error("❌ Failed to initialize primary database.", e);
            // 重新抛出 RuntimeException 确保启动失败，以便于调试
            throw new RuntimeException("Primary database initialization failed.", e);
        }
    }
}