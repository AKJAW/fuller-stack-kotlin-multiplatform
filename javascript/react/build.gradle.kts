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
    implementation(ReactLibs.REACT_ROUTER)
    implementation(ReactLibs.REDUX)
    implementation(ReactLibs.REACT_REDUX)
    implementation(ReactLibs.REACT_DOM)
    implementation(ReactLibs.STYLED)
    implementation(ReactLibs.CSS_JS)

    // react libraries wrappers
    implementation(ReactLibs.MUIRWIK)

    // dependency injection
    implementation(ReactLibs.KODEIN_DI_FRAMEWORK_JAVASCRIPT)

    // network
    implementation(SharedLibs.COROUTINES_CORE)
    implementation(SharedLibs.KOTLINX_SERIALIZATION)
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
        implementation(npm("react-router-dom", Versions.REACT_ROUTER))

        implementation(npm("styled-components", "5.0.0"))
        implementation(npm("inline-style-prefixer", "6.0.0"))
        implementation(npm("@material-ui/core", Versions.NPM_MATERIAL_UI))
        implementation(npm("@material-ui/icons", Versions.NPM_MATERIAL_UI_ICONS))
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
