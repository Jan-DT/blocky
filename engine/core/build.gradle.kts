plugins {
    id("java")
    id("java-library")
}

group = "nl.jandt.blocky.engine.core"
version = "0.1-INDEV"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.11.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    compileOnly("org.jetbrains:annotations:24.0.0")

    implementation("org.slf4j:slf4j-api:2.0.16")
    implementation("org.reflections:reflections:0.10.2")

    // I am still unsure how to treat Minestom as a dependency
    // on the one hand I feel like core should not at all depend on it,
    // but on the other hand I think things like Events should be a core implementation.
    // If anyone has a better idea, let me know! - Jan
    api("net.minestom:minestom-snapshots:4305006e6b")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}
