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

    compileOnly(deps.annotations)
    implementation(deps.slf4j)

    // I am still unsure how to treat Minestom as a dependency.
    // I feel like engine.core should not depend on Minestom,
    // but for now we rely on Minestom Events and Scheduling.
    // Maybe someday we can implement this ourselves?
    // If anyone has any thoughts on this, let me know! - Jan
    implementation(deps.minestom)
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}
