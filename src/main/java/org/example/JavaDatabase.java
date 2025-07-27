package org.example;

import java.util.ArrayList;
import org.example.shared.Config;
import org.example.shared.DataSourceKt;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.extensibility.Plugin;
import org.flywaydb.core.internal.plugin.PluginRegister;
import org.flywaydb.core.internal.resource.CoreResourceTypeProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaDatabase {
    private static final String REGISTER_PLUGINS_FIELD = "REGISTERED_PLUGINS";

    private static final Logger LOG = LoggerFactory.getLogger(JavaDatabase.class);

    public static void runFlywayMigrations(final Config config) {
        var flywayConfig = Flyway.configure()
            .dataSource(DataSourceKt.createDataSource());

        if (config.getEnableNamingValidation()) {
            flywayConfig = flywayConfig.validateMigrationNaming(true);
        }

        final var flyway = flywayConfig.load();
        if (config.getEnableHackyFix()) {
            registerCoreResourceTypeProviderIfMissing(flyway);
        }
        flyway.migrate();
    }

    private static void registerCoreResourceTypeProviderIfMissing(final Flyway flyway) {
        try {
            final var pluginRegister = flyway.getConfiguration().getPluginRegister();
            final var field = PluginRegister.class.getDeclaredField(REGISTER_PLUGINS_FIELD);
            field.setAccessible(true);
            @SuppressWarnings("unchecked") final var pluginList = (ArrayList<Plugin>) field.get(pluginRegister);
            if (pluginList.stream().noneMatch(plugin -> plugin instanceof CoreResourceTypeProvider)) {
                pluginList.add(new CoreResourceTypeProvider());
            }
        } catch (final NoSuchFieldException | IllegalAccessException ex) {
            throw new RuntimeException("Incorrect registration of the hacky fix!", ex);
        }
    }
}
