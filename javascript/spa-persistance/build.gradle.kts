plugins {
    id("org.jetbrains.kotlin.js")
}

repositories {
    maven("https://kotlin.bintray.com/kotlin-js-wrappers/")
    maven("https://dl.bintray.com/kotlin/kotlinx")
    maven("https://dl.bintray.com/cfraser/muirwik")
}

dependencies {
    implementation(project(":shared"))

    implementation(kotlin("stdlib-js"))

    // network
    implementation(ReactLibs.COROUTINES_JS)
    implementation(ReactLibs.SERIALIZATION_RUNTIME_JS)
}

kotlin {
    target {
        browser()
    }

    sourceSets["main"].dependencies { }
}

tasks {
    val reactRun by registering {
        dependsOn("browserDevelopmentRun")

        doLast {
            println("running on http://localhost:8080/")
        }
    }
}
