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

    compileOnly(deps.annotations)
    implementation(deps.slf4j)
    implementation(deps.reflections)

    implementation(project(":engine:core"))
    implementation(deps.minestom)
    implementation(deps.minimessage)
    implementation(deps.polar)
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}
