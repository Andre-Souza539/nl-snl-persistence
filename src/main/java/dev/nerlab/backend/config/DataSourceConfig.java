package dev.nerlab.backend.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class DataSourceConfig {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    @Value("${datasource.url}")
    private String databaseUrl;
    @Value("${datasource.username}")
    private String databaseUser;
    @Value("${datasource.password}")
    private String databasePassword;
    @Value("${datasource.driver-class-name}")
    private String databaseDriver;

    private static HikariDataSource dataSource;

    @Bean
    public DataSource datasource() throws SQLException {
        logger.info("Configuring DataSource with URL: {}", databaseUrl);
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setJdbcUrl(databaseUrl);
        hikariConfig.setUsername(databaseUser);
        hikariConfig.setPassword(databasePassword);
        hikariConfig.setDriverClassName(databaseDriver);

        dataSource = new HikariDataSource(hikariConfig);
        return dataSource;
    }


}