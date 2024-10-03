plugins {
    kotlin("jvm") version "2.0.20"
}

group = project.group
version = project.version

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    implementation(project(":engine"))
    implementation(project(":kotlin"))

    implementation(deps.minestom)

    implementation(deps.kotlinlogging)
    implementation(deps.logback.core)
    implementation(deps.logback.classic)
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

kotlin {
    jvmToolchain(project.property("javaVersion").toString().toInt())
}
