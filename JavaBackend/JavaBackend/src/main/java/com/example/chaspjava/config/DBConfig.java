package com.example.chaspjava.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;
// Spring configuration class
@Configuration
public class DBConfig {
    // Registers the DataSource as a Spring-managed bean
    @Bean
    public DataSource dataSource() {
        // Registers the DataSource as a Spring-managed bean
        return DataSourceBuilder.create()
                // JDBC URL for connecting to the CHaSP MySQL database
                .url("jdbc:mysql://localhost:3306/chaspDB")
                // Database Username
                .username("root")
                /* Database Password;
                 Password is masked to prevent credential exposure and to demonstrate secure coding practices;
                 in real-world systems, sensitive values are stored using environment variables or secure vaults */
                .password("Lathu2003@")
                // MySQL JDBC driver class
                .driverClassName("com.mysql.cj.jdbc.Driver")
                // Builds and returns the DataSource instance
                .build();
    }
}



