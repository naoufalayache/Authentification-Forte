package com.naoufalayache.authentication.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfig {

    @Value("${spring.datasource.url}")
    private String host;

    @Value("${spring.datasource.username}")
    private String user;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public CommandLineRunner migrateFlyway() {
        return args -> {
            Flyway flyway = Flyway.configure()
                .dataSource(
                    host,
                    user,
                    password
                )
                .locations("classpath:db/migrations")
                .baselineOnMigrate(true)
                .load();

            flyway.migrate();
        };
    }
}