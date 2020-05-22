plugins {
    id("org.jetbrains.kotlin.js")
}

repositories {
    maven("https://kotlin.bintray.com/kotlin-js-wrappers/")
    maven("https://dl.bintray.com/kotlin/kotlinx")
}

dependencies {
    implementation(project(":shared"))

    implementation(kotlin("stdlib-js"))

    // Kotlin wrappers
    implementation(ReactLibs.HTML_JS)
    implementation(ReactLibs.REACT)
    implementation(ReactLibs.REACT_DOM)
    implementation(ReactLibs.STYLED)

    // dependency injection
    implementation(ReactLibs.KODEIN_DI_ERASED_JS)
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
    }
}

tasks {
    val reactRun by registering {
        dependsOn("browserRun")
    }
}
