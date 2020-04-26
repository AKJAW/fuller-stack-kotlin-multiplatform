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
}

allprojects {
    version = "0.0.1"
}