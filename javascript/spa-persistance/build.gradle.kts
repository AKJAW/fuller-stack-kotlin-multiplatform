plugins {
    id("org.jetbrains.kotlin.js")
}

dependencies {
    implementation(project(":shared"))

    implementation(kotlin("stdlib-js"))

    // network
    implementation(SharedLibs.COROUTINES_CORE)
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
