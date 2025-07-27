package org.example;

import org.example.shared.Config;
import org.example.shared.ConfigKt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaApplication {
    private static final Logger LOG = LoggerFactory.getLogger(JavaApplication.class);

    public static void main(String[] args) {
        final Config config = ConfigKt.loadConfigFromEnv();
        JavaDatabase.runFlywayMigrations(config);
        LOG.info("Migrations done!");
    }
}
