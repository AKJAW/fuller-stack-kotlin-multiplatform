buildscript {
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven("https://plugins.gradle.org/m2/")
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${PluginsVersions.KOTLIN}")
        classpath("com.android.tools.build:gradle:${PluginsVersions.GRADLE_BUILD_TOOLS}")
        classpath("de.mannodermaus.gradle.plugins:android-junit5:${PluginsVersions.ANDROID_JUNIT5}")
    }
}

plugins {
//    kotlin("multiplatform") version PluginsVersions.KOTLIN
    kotlin("plugin.serialization") version PluginsVersions.KOTLIN
    id("io.gitlab.arturbosch.detekt") version PluginsVersions.DETEKT
    id("org.jlleitschuh.gradle.ktlint") version PluginsVersions.KTLINT
}

allprojects {
    version = "0.0.1"

    repositories {
        google()
        mavenCentral()
        jcenter()
    }
}

subprojects {
    apply {
        plugin("io.gitlab.arturbosch.detekt")
        plugin("org.jlleitschuh.gradle.ktlint")
        plugin("org.jetbrains.kotlin.plugin.serialization")
    }

    detekt {
        buildUponDefaultConfig = true // preconfigure defaults
        config = files("${project.rootDir}/config/detekt/detekt.yml") // point to your custom config defining rules to run, overwriting default behavior
        baseline = file("${project.rootDir}/config/detekt/baseline.xml") // a way of suppressing issues before introducing detekt

        reports {
            html {
                enabled = true
                destination = file("build/reports/detekt.html")
            }
        }
    }

    ktlint {
        debug.set(false)
        version.set(Versions.KTLINT)
        verbose.set(true)
        android.set(false)
        outputToConsole.set(true)
        ignoreFailures.set(true)
        enableExperimentalRules.set(true)
        filter {
            exclude("**/generated/**")
            include("**/kotlin/**")
        }
    }
}
