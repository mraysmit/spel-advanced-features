# Maven Migration Guide

This project has been migrated from Gradle to Maven. The following files have been created or modified:

- `pom.xml`: Maven project configuration file

## Completing the Migration

To complete the migration, you need to manually remove the following Gradle-specific files:

1. `build.gradle`: Gradle build script
2. `gradle/wrapper/gradle-wrapper.jar`: Gradle wrapper JAR
3. `gradle/wrapper/gradle-wrapper.properties`: Gradle wrapper properties
4. `gradlew`: Gradle wrapper script for Unix-like systems
5. `gradlew.bat`: Gradle wrapper script for Windows

You can remove these files using the following commands:

```bash
# Remove build.gradle
rm build.gradle

# Remove gradle directory
rm -rf gradle

# Remove gradlew and gradlew.bat
rm gradlew
rm gradlew.bat
```

## Building with Maven

After removing the Gradle files, you can build the project using Maven:

```bash
# Compile the project
mvn compile

# Run tests
mvn test

# Package the project
mvn package

# Run the application
java -jar target/spel-advanced-features-1.0-SNAPSHOT-jar-with-dependencies.jar
```

## Project Structure

The project structure remains unchanged and follows Maven conventions:

- `src/main/java`: Source code
- `src/test/java`: Test code

## Dependencies

The following dependencies have been configured in the Maven project:

- Spring Expression (6.1.3)
- Spring Context (6.1.3)
- JUnit Jupiter (5.10.1) for testing