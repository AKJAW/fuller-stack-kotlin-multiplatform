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

    //Kotlin wrappers
    implementation("org.jetbrains.kotlinx:kotlinx-html-js:0.7.1")
    implementation("org.jetbrains:kotlin-react:16.13.1-pre.102-kotlin-1.3.72")
    implementation("org.jetbrains:kotlin-react-dom:16.13.1-pre.102-kotlin-1.3.72")
    implementation("org.jetbrains:kotlin-styled:1.0.0-pre.102-kotlin-1.3.72")

    //dependency injection
    implementation("org.kodein.di:kodein-di-erased-js:6.5.5")
}

kotlin {
    target {
        browser()
    }

    sourceSets["main"].dependencies {
        implementation(npm("react", "16.13.1"))
        implementation(npm("react-dom", "16.13.1"))

        implementation(npm("styled-components"))
        implementation(npm("inline-style-prefixer"))
    }
}