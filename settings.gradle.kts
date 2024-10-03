plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version("0.5.0")
}

rootProject.name = "blocky"

dependencyResolutionManagement {
    versionCatalogs {
        create("deps") {
            // core dependencies
            library("minestom", "net.minestom:minestom-snapshots:38b3ad3a10")

            // implemenatation libraries
            library("polar", "dev.hollowcube:polar:1.11.3")
            library("minimessage", "net.kyori:adventure-text-minimessage:4.17.0")

            // server dependencies
            library("logback-core", "ch.qos.logback:logback-core:1.5.8")
            library("logback-classic", "ch.qos.logback:logback-classic:1.5.8")
            library("reflections", "org.reflections:reflections:0.10.2")

            // java specific
            library("slf4j", "org.slf4j:slf4j-api:2.0.16")
            library("annotations", "org.jetbrains:annotations:24.0.0")

            // kotlin specific
            library("kotlinlogging", "io.github.oshai:kotlin-logging-jvm:7.0.0")
            library("kotstom", "net.bladehunt:kotstom:0.4.0-alpha.4")
        }
    }
}

include("engine", "engine:core", "engine:impl")
include("kotlin")
include("examples:example-java", "examples:example-kotlin")
