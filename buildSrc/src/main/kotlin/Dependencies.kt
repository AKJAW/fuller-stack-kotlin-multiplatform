object Versions {
    //shared
    const val KODEIN = "6.5.5"
    const val COROUTINES = "1.3.7"
    const val KTLINT = "0.36.0"

    //shared test
    const val JUNIT5 = "5.6.2"

    //react
    const val KOTLINX_HTML_JS = "0.7.1"
    const val REACT = "16.13.1"
    const val REDUX = "4.0.0"
    const val REACT_REDUX = "5.0.7"
    const val MUIRWIK = "0.5.1"

    //ktor
    const val KTOR = "1.3.2"
    const val LOG_BACK = "1.2.3"

    //android
    const val MIN_SDK_VERSION = 21
    const val TARGET_SDK_VERSION = 29
    const val COMPILE_SDK_VERSION = 29
    const val APP_COMPAT = "1.1.0"
    const val MATERIAL = "1.1.0"
    const val CONSTRAINT_LAYOUT = "2.0.0-beta4"
    const val LIFECYCLE_RUNTIME_KTX = "2.2.0"

    //android test
    const val ANDROIDX_TEST = "1.1.0"
    const val ESPRESSO_CORE = "3.2.0"
}

object PluginsVersions {
    const val KOTLIN = "1.3.72"
    const val GRADLE_BUILD_TOOLS = "3.4.2"
    const val AGP = "3.6.3"
    const val DETEKT = "1.9.1"
    const val KTLINT = "9.2.1"
    const val VERSIONS_PLUGIN = "0.28.0"
}

object SharedLibs {
    const val KODEIN_DI_ERASED = "org.kodein.di:kodein-di-erased:${Versions.KODEIN}"
    const val COROUTINES_COMMON =  "org.jetbrains.kotlinx:kotlinx-coroutines-core-common:${Versions.COROUTINES}"
    const val COROUTINES_CORE =  "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINES}"
}

object JVMTestingLibs {
    const val JUNIT5 = "org.junit.jupiter:junit-jupiter:${Versions.JUNIT5}"
    const val COROUTINES_TEST =  "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.COROUTINES}"
}

object ReactLibs {
    const val HTML_JS = "org.jetbrains.kotlinx:kotlinx-html-js:${Versions.KOTLINX_HTML_JS}"
    const val REACT = "org.jetbrains:kotlin-react:${Versions.REACT}-pre.104-kotlin-${PluginsVersions.KOTLIN}"
    const val REDUX = "org.jetbrains:kotlin-redux:${Versions.REDUX}-pre.104-kotlin-${PluginsVersions.KOTLIN}"
    const val REACT_REDUX = "org.jetbrains:kotlin-react-redux:${Versions.REACT_REDUX}-pre.104-kotlin-${PluginsVersions.KOTLIN}"
    const val REACT_DOM = "org.jetbrains:kotlin-react-dom:${Versions.REACT}-pre.104-kotlin-${PluginsVersions.KOTLIN}"
    const val STYLED = "org.jetbrains:kotlin-styled:1.0.0-pre.104-kotlin-${PluginsVersions.KOTLIN}"
    const val CSS_JS = "org.jetbrains:kotlin-css-js:1.0.0-pre.104-kotlin-${PluginsVersions.KOTLIN}"
    const val MUIRWIK = "com.ccfraser.muirwik:muirwik-components:${Versions.MUIRWIK}"

    const val KODEIN_DI_ERASED_JS = "org.kodein.di:kodein-di-erased-js:${Versions.KODEIN}"

    const val COROUTINES_JS = "org.jetbrains.kotlinx:kotlinx-coroutines-core-js:${Versions.COROUTINES}"
}

object KtorLibs {
    const val SERVER_CORE = "io.ktor:ktor-server-core:${Versions.KTOR}"
    const val SERVER_NETTY = "io.ktor:ktor-server-netty:${Versions.KTOR}"
    const val LOG_BACK_CLASSIC = "ch.qos.logback:logback-classic:${Versions.LOG_BACK}"

    const val KODEIN_DI_ERASED_JVM = "org.kodein.di:kodein-di-erased-jvm:${Versions.KODEIN}"
    const val KODEIN_DI_FRAMEWORK_KTOR_SERVER = "org.kodein.di:kodein-di-framework-ktor-server-jvm:${Versions.KODEIN}"
}

object AndroidLibs {
    const val KOTLIN_JDK = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.KTOR}"

    const val APP_COMPAT = "androidx.appcompat:appcompat:${Versions.APP_COMPAT}"
    const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"
    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}"

    const val KODEIN_DI_ERASED_JVM = "org.kodein.di:kodein-di-erased-jvm:${Versions.KODEIN}"
    const val KODEIN_DI_FRAMEWORK_ANDROID_X = "org.kodein.di:kodein-di-framework-android-x:${Versions.KODEIN}"

    const val COROUTINES_ANDROID =  "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.COROUTINES}"
    const val LIFECYCLE_RUNTIME_KTX =  "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE_RUNTIME_KTX}"
}

object AndroidTestingLibs {
    const val ANDROIDX_TEST_EXT_JUNIT = "androidx.test.ext:junit:${Versions.ANDROIDX_TEST}"
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_CORE}"
}