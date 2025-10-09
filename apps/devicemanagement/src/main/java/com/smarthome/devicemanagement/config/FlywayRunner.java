package com.smarthome.devicemanagement.config;

import jakarta.annotation.PostConstruct;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class FlywayRunner {

    private static final Logger logger = LoggerFactory.getLogger(FlywayRunner.class);

    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void runFlyway() {
        try {
            Flyway flyway = Flyway.configure()
                    .dataSource(dataSource)
                    .locations("classpath:db/migration")
                    .baselineOnMigrate(true)
                    .load();

            MigrationInfo current = flyway.info().current();
            if (current == null) {
                logger.info("No existing migrations found, running baseline");
            }

            flyway.migrate();
            logger.info("Flyway migration completed successfully");

        } catch (Exception e) {
            logger.error("Flyway migration failed", e);
        }
    }
}
