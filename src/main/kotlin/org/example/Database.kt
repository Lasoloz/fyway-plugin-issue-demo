package org.example

import org.example.shared.Config
import org.example.shared.createDataSource
import org.flywaydb.core.Flyway
import org.flywaydb.core.extensibility.Plugin
import org.flywaydb.core.internal.plugin.PluginRegister
import org.flywaydb.core.internal.resource.CoreResourceTypeProvider

private const val REGISTERED_PLUGINS_FIELD = "REGISTERED_PLUGINS"

fun runFlywayMigrations(config: Config) {
    val dataSource = createDataSource()

    Flyway.configure()
        .dataSource(dataSource)
        .apply {
            if (config.enableNamingValidation) {
                validateMigrationNaming(true)
            }
        }
        .load()
        .apply {
            if (config.enableHackyFix) {
                registerCoreResourceTypeProviderIfMissing()
            }
        }
        .migrate()
}

private fun Flyway.registerCoreResourceTypeProviderIfMissing(): Flyway = apply {
    val pluginRegister = this.configuration.pluginRegister
    val field = PluginRegister::class.java.getDeclaredField(REGISTERED_PLUGINS_FIELD)
    field.setAccessible(true)
    @Suppress("UNCHECKED_CAST")
    val pluginList = (field.get(pluginRegister) as ArrayList<Plugin>)
    if (pluginList.none { plugin -> plugin is CoreResourceTypeProvider }) {
        pluginList.add(CoreResourceTypeProvider())
    }
}
