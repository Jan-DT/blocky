plugins {
    kotlin("jvm") version "2.0.20"
}

group = "nl.jandt.blocky"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    implementation(project(":engine"))
    implementation(deps.minestom)
    implementation(deps.minimessage)
    implementation(deps.polar)
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}
