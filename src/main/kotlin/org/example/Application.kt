package org.example

import org.example.shared.loadConfigFromEnv
import org.slf4j.Logger
import org.slf4j.LoggerFactory

val log: Logger = LoggerFactory.getLogger("MainLogger")

fun main() {
    val config = loadConfigFromEnv()
    runFlywayMigrations(config)
    log.info("Migrations done!")
}
