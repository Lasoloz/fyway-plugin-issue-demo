package org.example.shared

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import javax.sql.DataSource

fun createDataSource(): DataSource {
    val config = HikariConfig().apply {
        driverClassName = "org.postgresql.Driver"
        jdbcUrl = "jdbc:postgresql://localhost:6999/localdev"
        username = "localdev"
        password = ""
        maximumPoolSize = 10

        validate()
    }

    return HikariDataSource(config)
}
