plugins {
    id("java")
    id("io.github.goooler.shadow") version "8.1.7"
}

group = "nl.jandt.blocky.example"
version = project.version

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(project.property("java_version").toString())
    }
}

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}


dependencies {
    implementation(project(":engine:core"))
    implementation(project(":engine:impl"))
    implementation("ch.qos.logback:logback-core:1.5.6")
    implementation("ch.qos.logback:logback-classic:1.5.6")
    implementation("org.slf4j:slf4j-api:2.0.16")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.withType<Jar> {
    if (project.hasProperty("base_name")) {
        archiveBaseName = project.property("base_name").toString()
    }

    manifest {
        attributes["Main-Class"] = "nl.jandt.blocky.engine.BlockyEngine"
    }
}
