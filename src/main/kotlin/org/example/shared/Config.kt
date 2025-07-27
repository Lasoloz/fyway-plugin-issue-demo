package org.example.shared

private const val ENABLE_NAMING_VALIDATION_ENV = "ENABLE_NAMING_VALIDATION"
private const val ENABLE_HACKY_FIX_ENV = "ENABLE_HACKY_FIX"

data class Config(val enableNamingValidation: Boolean, val enableHackyFix: Boolean)

fun loadConfigFromEnv(): Config {
    return System.getenv().let { env ->
        Config(
            enableNamingValidation = env[ENABLE_NAMING_VALIDATION_ENV]?.toBooleanStrictOrNull() ?: false,
            enableHackyFix = env[ENABLE_HACKY_FIX_ENV]?.toBooleanStrictOrNull() ?: false,
        )
    }
}
