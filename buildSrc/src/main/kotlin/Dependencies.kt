object Versions {

    //shared
    const val KODEIN = "7.0.0"
    const val COROUTINES = "1.3.7"
    const val KTLINT = "0.36.0"
    const val KLOCK = "1.11.11"
    const val SERIALIZATION = "0.20.0"

    //shared test
    const val JUNIT5 = "5.6.2"
    const val MOCKK = "1.10.0"

    //react
    const val KOTLINX_HTML_JS = "0.7.1"
    const val REACT = "16.13.1"
    const val REDUX = "4.0.0"
    const val REACT_REDUX = "5.0.7"
    const val MUIRWIK = "0.5.1"
    const val NPM_METRIAL_UI = "^4.9.14"

    //ktor
    const val KTOR = "1.3.2"
    const val LOG_BACK = "1.2.3"
    const val EXPOSED = "0.26.1"
    const val H2 = "1.4.199"
    const val POSTGRE_SQL = "42.2.2"
    const val HIKARI_CP = "3.4.5"

    //android
    const val MIN_SDK_VERSION = 21
    const val TARGET_SDK_VERSION = 29
    const val COMPILE_SDK_VERSION = 29
    const val APP_COMPAT = "1.1.0"
    const val MATERIAL = "1.2.0-beta01"
    const val CONSTRAINT_LAYOUT = "2.0.0-beta4"
    const val LIFECYCLE = "2.2.0"
    const val SIMPLE_STACK = "2.3.2"
    const val SIMPLE_STACK_EXTENSIONS = "2.0.0"
    const val RETROFIT = "2.9.0"
    const val RETROFIT_KOTLINX_SERIALIZATION = "0.5.0"
    const val OKHTTP = "4.8.0"
    const val FRAGMENT_KTX = "1.2.5"
    const val LEAK_CANARY = "2.4"
    const val TIMBER = "4.7.1"
    const val ROOM = "2.2.5"

    //android test
    const val ANDROIDX_TEST = "1.1.0"
    const val ESPRESSO_CORE = "3.2.0"
}

object PluginsVersions {
    const val KOTLIN = "1.3.72"
    const val GRADLE_BUILD_TOOLS = "3.5.0"
    const val ANDROID_JUNIT5 = "1.6.2.0"
    const val DETEKT = "1.9.1"
    const val KTLINT = "9.2.1"
}

object SharedLibs {
    const val KODEIN_DI = "org.kodein.di:kodein-di:${Versions.KODEIN}"
    const val COROUTINES_COMMON =  "org.jetbrains.kotlinx:kotlinx-coroutines-core-common:${Versions.COROUTINES}"
    const val COROUTINES_CORE =  "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINES}"
    const val KLOCK =  "com.soywiz.korlibs.klock:klock:${Versions.KLOCK}"
    const val SERIALIZATION_RUNTIME_COMMON = "org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:${Versions.SERIALIZATION}"

}

object SharedTestingLibs {
    const val MOCKK = "io.mockk:mockk:${Versions.MOCKK}"
}

object JVMLibs {
    const val SERIALIZATION_RUNTIME_JVM = "org.jetbrains.kotlinx:kotlinx-serialization-runtime:${Versions.SERIALIZATION}"
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

    const val COROUTINES_JS = "org.jetbrains.kotlinx:kotlinx-coroutines-core-js:${Versions.COROUTINES}"
    const val SERIALIZATION_RUNTIME_JS = "org.jetbrains.kotlinx:kotlinx-serialization-runtime-js:${Versions.SERIALIZATION}"
    const val KTOR_CLIENT_JS = "io.ktor:ktor-client-js:${Versions.KTOR}"
    const val KTOR_CLIENT_JSON = "io.ktor:ktor-client-json-js:${Versions.KTOR}"
    const val KTOR_CLIENT_SERIALIZATION = "io.ktor:ktor-client-serialization-js:${Versions.KTOR}"
}

object KtorLibs {
    const val SERVER_CORE = "io.ktor:ktor-server-core:${Versions.KTOR}"
    const val SERVER_NETTY = "io.ktor:ktor-server-netty:${Versions.KTOR}"
    const val LOG_BACK_CLASSIC = "ch.qos.logback:logback-classic:${Versions.LOG_BACK}"

    const val KODEIN_DI_FRAMEWORK_KTOR_SERVER = "org.kodein.di:kodein-di-framework-ktor-server-jvm:${Versions.KODEIN}"

    const val KTOR_SERIALIZATION = "io.ktor:ktor-serialization:${Versions.KTOR}"
    
    const val EXPOSED_CORE = "org.jetbrains.exposed:exposed-core:${Versions.EXPOSED}"
    const val EXPOSED_DAO = "org.jetbrains.exposed:exposed-dao:${Versions.EXPOSED}"
    const val EXPOSED_JDBC = "org.jetbrains.exposed:exposed-jdbc:${Versions.EXPOSED}"
    const val H2 = "com.h2database:h2:${Versions.H2}"
    const val POSTGRE_SQL = "org.postgresql:postgresql:${Versions.POSTGRE_SQL}"
    const val HIKARI_CP = "com.zaxxer:HikariCP:${Versions.HIKARI_CP}"
}

object AndroidLibs {
    const val KOTLIN_JDK = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.KTOR}"

    const val APP_COMPAT = "androidx.appcompat:appcompat:${Versions.APP_COMPAT}"
    const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"
    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}"
    const val LIFECYCLE_VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE}"
    const val LIFECYCLE_LIVEDATA = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.LIFECYCLE}"
    const val LIFECYCLE_SAVEDSTATE = "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.LIFECYCLE}"
    const val LIFECYCLE_RUNTIME_KTX =  "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE}"
    const val FRAGMENT_KTX =  "androidx.fragment:fragment-ktx:${Versions.FRAGMENT_KTX}"

    const val KODEIN_DI_FRAMEWORK_ANDROID_X = "org.kodein.di:kodein-di-framework-android-x:${Versions.KODEIN}"

    const val COROUTINES_ANDROID =  "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.COROUTINES}"
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val RETROFIT_KOTLINX_SERIALIZATION = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.RETROFIT_KOTLINX_SERIALIZATION}"
    const val OKHTTP = "com.squareup.okhttp3:okhttp:${Versions.OKHTTP}"
    const val OKHTTP_LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP}"

    const val ROOM_RUNTIME = "androidx.room:room-runtime:${Versions.ROOM}"
    const val ROOM_COMPILER = "androidx.room:room-compiler:${Versions.ROOM}"
    const val ROOM_KTX = "androidx.room:room-ktx:${Versions.ROOM}"


    const val SIMPLE_STACK = "com.github.Zhuinden:simple-stack:${Versions.SIMPLE_STACK}"
    const val SIMPLE_STACK_EXTENSIONS = "com.github.Zhuinden:simple-stack-extensions:${Versions.SIMPLE_STACK_EXTENSIONS}"

    const val LEAK_CANARY = "com.squareup.leakcanary:leakcanary-android:${Versions.LEAK_CANARY}"
    const val TIMBER = "com.jakewharton.timber:timber:${Versions.TIMBER}"
}

object AndroidTestingLibs {
    const val ANDROIDX_TEST_EXT_JUNIT = "androidx.test.ext:junit:${Versions.ANDROIDX_TEST}"
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_CORE}"
}