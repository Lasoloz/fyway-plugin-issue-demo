# Description of the issue

- See [linked issue](https://github.com/flyway/flyway/issues/4112)

# Steps to start & debug the application

1. Run a Postgres database on port 6999 (or use Docker with the provided compose file)
2. Install the proper Java version (21)
    - For an easier setup, nix users can use direnv and the provided flake in `devshell`
3. Build the application using `./gradlew buildFatJar` (or `./gradlew buildFatJar -PbuildJavaVersion=true` for a JAR
   defaulting to the `JavaApplication` class)
4. Run the application
    1. Running the JAR file with `java -jar build/libs/flyway-plugin-issue-demo-all.jar` does nothing
        - Adding the `ENABLE_NAMING_VALIDATION=true` environment variable adds `validateMigrationNaming(true)` to the
          fluent config, resulting in an exception
        - Adding the `ENABLE_HACKY_FIX=true` adds enables the reflection-based fix between the load and migrate steps
    2. Running the application from IntelliJ (the `ApplicationKt` or the `JavaApplication` run configuration) applies
       all the migrations properly creating the tables defined in the migration files.

# Fix to the issue:

The `shadowJar` task has a method called `mergeServiceFiles`, which need to be applied for a proper űber-jar build in
Gradle:

```kotlin
tasks {
    named<ShadowJar>("shadowJar") {
        mergeServiceFiles()
    }
}
```
