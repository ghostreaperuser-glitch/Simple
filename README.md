# BusinessApp

Simple demo project: JavaFX desktop CRUD app for learning ERP-style features.

## Quick start

- Build: `./gradlew clean build`
- Run desktop (GUI required): `./gradlew runDesktop` (recommended for development)
- Build runnable jar: `./gradlew bootJar` -> `build/libs/*.jar` (then run: `java -jar build/libs/<name>.jar`)

**Notes:**

- Ensure you run `runDesktop` or the jar on a machine with a display (Windows/macOS/Linux).
- If JavaFX modules are not present on the classpath, `runDesktop` includes JVM args to add required modules. If you use `java -jar`, you may need to run with: `java --add-modules=javafx.controls,javafx.fxml -jar build/libs/<name>.jar`.
- On Windows, verify `JAVA_HOME` points to a JDK 17 and that `java` is on your PATH.
- If graphics issues occur when running remotely, try `-Dprism.order=sw` to force software rendering.

## Development

- Java 17 (toolchain configured in Gradle)
- Use `./gradlew classes` to compile

## CI

GitHub Actions workflow runs `./gradlew build` on push/PR.
