buildscript {
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven("https://plugins.gradle.org/m2/")
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
    }

    //TODO version to separate file
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.61")
        classpath("com.android.tools.build:gradle:3.4.2")
    }
}

plugins {
    kotlin("multiplatform") version "1.3.61"
    id("io.gitlab.arturbosch.detekt") version "1.8.0"
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
}
