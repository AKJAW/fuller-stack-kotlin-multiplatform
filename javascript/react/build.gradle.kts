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
    implementation(project(":javascript:spa-authentication"))
    implementation(project(":javascript:spa-persistance"))

    implementation(kotlin("stdlib-js"))

    // Kotlin wrappers
    implementation(ReactLibs.HTML_JS)
    implementation(ReactLibs.REACT)
    implementation(ReactLibs.REDUX)
    implementation(ReactLibs.REACT_REDUX)
    implementation(ReactLibs.REACT_DOM)
    implementation(ReactLibs.STYLED)
    implementation(ReactLibs.CSS_JS)

    // react libraries wrappers
    implementation(ReactLibs.MUIRWIK)

    // dependency injection
    implementation(SharedLibs.KODEIN_DI)

    // network
    implementation(ReactLibs.COROUTINES_JS)
    implementation(ReactLibs.SERIALIZATION_RUNTIME_JS)
    implementation(ReactLibs.KTOR_CLIENT_JS)
    implementation(ReactLibs.KTOR_CLIENT_JSON)
    implementation(ReactLibs.KTOR_CLIENT_SERIALIZATION)

    // date
    implementation(SharedLibs.KLOCK)
}

kotlin {
    target {
        browser()
    }

    sourceSets["main"].dependencies {
        implementation(npm("react", Versions.REACT))
        implementation(npm("react-dom", Versions.REACT))

        implementation(npm("styled-components"))
        implementation(npm("inline-style-prefixer"))
        implementation(npm("@material-ui/core", Versions.NPM_METRIAL_UI))
        implementation(npm("@material-ui/icons"))
    }
}

tasks {
    val reactRun by registering {
        dependsOn("browserDevelopmentRun")

        doLast {
            println("running on http://localhost:8080/")
        }
    }
}
