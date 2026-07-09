package dev.nerlab.backend.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

@Slf4j
@Configuration
public class DataSourceConfig {


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
        log.info("Configuring DataSource with URL: {}", databaseUrl);
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setJdbcUrl(databaseUrl);
        hikariConfig.setUsername(databaseUser);
        hikariConfig.setPassword(databasePassword);
        hikariConfig.setDriverClassName(databaseDriver);

        dataSource = new HikariDataSource(hikariConfig);
        return dataSource;
    }


}