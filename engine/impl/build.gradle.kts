plugins {
    id("java")
    id("java-library")
}

group = "nl.jandt.blocky.engine.impl"
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

    implementation("ch.qos.logback:logback-core:1.5.6")
    implementation("ch.qos.logback:logback-classic:1.5.6")
    implementation("org.slf4j:slf4j-api:2.0.16")
    implementation("org.reflections:reflections:0.10.2")

    implementation(project(":engine:core"))

    api("net.kyori:adventure-text-minimessage:4.17.0")
    api("net.minestom:minestom-snapshots:6c5cd6544e")
    api("dev.hollowcube:polar:1.11.1")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}
