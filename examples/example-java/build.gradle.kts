plugins {
    id("java")
    id("io.github.goooler.shadow") version "8.1.7"
}

group = project.group
version = project.version

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}


dependencies {
    implementation(project(":engine"))

    implementation(deps.minestom)

    implementation(deps.slf4j)
    implementation(deps.logback.core)
    implementation(deps.logback.classic)
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(project.property("javaVersion").toString())
    }
}
