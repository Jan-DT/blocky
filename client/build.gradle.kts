plugins {
    id("java")
    id("io.github.goooler.shadow") version "8.1.7"
}

group = "nl.jandt.blocky.client"
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
    // testing
    testImplementation(platform("org.junit:junit-bom:5.11.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // engine
    implementation(project(":engine"))

    // logging
    implementation("ch.qos.logback:logback-core:1.5.6")
    implementation("ch.qos.logback:logback-classic:1.5.6")
    implementation("org.slf4j:slf4j-api:2.0.16")

    // UI stuff
    implementation("com.formdev:flatlaf:3.5.1")
    implementation("com.formdev:flatlaf-extras:3.5.1")

    implementation("org.kordamp.ikonli:ikonli-materialdesign2-pack:12.3.1")
    implementation("org.kordamp.ikonli:ikonli-swing:12.3.1")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "nl.jandt.blocky.client.BlockyClient"
    }
}
