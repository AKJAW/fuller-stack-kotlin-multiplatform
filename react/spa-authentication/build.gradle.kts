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
}

kotlin {
    target {
        browser()
    }

    sourceSets["main"].dependencies {
        implementation(npm("react", Versions.REACT))
        implementation(npm("react-dom", Versions.REACT))
        implementation(npm("@auth0/auth0-react", Versions.AUTH0))

        implementation(npm("styled-components", "5.0.0"))
        implementation(npm("inline-style-prefixer", "6.0.0"))
        implementation(npm("@material-ui/core", Versions.NPM_MATERIAL_UI))
        implementation(npm("@material-ui/icons", Versions.NPM_MATERIAL_UI_ICONS))
    }
}
