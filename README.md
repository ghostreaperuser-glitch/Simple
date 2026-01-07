# BusinessApp

Simple demo project: JavaFX desktop CRUD app for learning ERP-style features.

## Quick start

- Build: `./gradlew clean build`
- Run desktop: `./gradlew runDesktop`
- Build runnable jar: `./gradlew bootJar` -> `build/libs/*.jar`

## Development

- Java 17 (toolchain configured in Gradle)
- Use `./gradlew classes` to compile

## CI

GitHub Actions workflow runs `./gradlew build` on push/PR.
