import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "2.1.20"
    // Using the ktor plugin, as in my use case that provides the `buildFatJar` task through the `application {}` config
    id("io.ktor.plugin") version "3.1.2"
    id("org.flywaydb.flyway") version "11.9.1"
}

group = "org.example"
version = "1.0-SNAPSHOT"

application {
    val buildJavaVersion = providers.gradleProperty("buildJavaVersion").orNull?.toBooleanStrictOrNull() ?: false
    if (buildJavaVersion) {
        println("Building Java version of fat jar")
        mainClass.set("org.example.JavaApplication")
    } else {
        println("Building Kotlin version of fat jar")
        mainClass.set("org.example.ApplicationKt")
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.postgresql:postgresql:42.7.3")
    implementation("com.zaxxer:HikariCP:6.3.0")
    implementation("org.flywaydb:flyway-core:11.9.1")
    implementation("org.flywaydb:flyway-database-postgresql:11.9.1")

    implementation("ch.qos.logback:logback-classic:1.5.13")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

// FIX: merging service files fixes the lookup issue:
//tasks {
//    named<ShadowJar>("shadowJar") {
//        mergeServiceFiles()
//    }
//}
