plugins {
    id("java")
    id("java-library")
    id("io.github.goooler.shadow") version "8.1.7"
}

group = "nl.jandt.blocky.engine"
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
    compileOnly("org.jetbrains:annotations:24.0.0")

    api(project(":engine:core"))
    api(project(":engine:impl"))
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "nl.jandt.blocky.engine.BlockyEngine"
    }
}
