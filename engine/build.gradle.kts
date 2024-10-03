// The :engine module just serves to bundle :engine:core and :engine:impl so it can be imported as one

plugins {
    id("java")
    id("java-library")
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
}

dependencies {
    api(project(":engine:core"))
    api(project(":engine:impl"))
}
