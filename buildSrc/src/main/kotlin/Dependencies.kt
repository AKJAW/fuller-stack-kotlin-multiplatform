object Versions {

    //shared
    const val KODEIN = "7.1.0"
    const val COROUTINES = "1.4.1"
    const val KTLINT = "0.36.0"
    const val KLOCK = "2.0.0-rc2"
    const val SERIALIZATION = "1.0.1"

    //shared test
    const val JUNIT5 = "5.6.2"
    const val MOCKK = "1.10.2"
    const val KOTEST = "4.3.1"

    //react
    const val KOTLINX_HTML_JS = "0.7.1"
    const val REACT = "16.14.0"
    const val REACT_ROUTER = "5.1.2"
    const val REDUX = "4.0.0"
    const val REACT_REDUX = "5.0.7"
    const val MUIRWIK = "0.6.0"
    const val NPM_MATERIAL_UI = "4.11.0"
    const val NPM_MATERIAL_UI_ICONS = "4.9.1"
    const val AUTH0 = "1.1.0"

    //ktor
    const val KTOR = "1.4.2"
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
    const val SIMPLE_STACK = "2.5.0"
    const val SIMPLE_STACK_EXTENSIONS = "2.1.0"
    const val RETROFIT = "2.9.0"
    const val RETROFIT_KOTLINX_SERIALIZATION = "0.8.0"
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
    const val KOTLIN = "1.4.10"
    const val GRADLE_BUILD_TOOLS = "4.0.1"
    const val ANDROID_JUNIT5 = "1.6.2.0"
    const val DETEKT = "1.9.1"
    const val KTLINT = "9.2.1"
}

object SharedLibs {
    const val KODEIN_DI = "org.kodein.di:kodein-di:${Versions.KODEIN}"
    const val COROUTINES_CORE =  "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINES}"
    const val KLOCK =  "com.soywiz.korlibs.klock:klock:${Versions.KLOCK}"
    const val KOTLINX_SERIALIZATION = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.SERIALIZATION}"
}

object SharedTestingLibs {
    const val KOTEST_ASSERTIONS = "io.kotest:kotest-assertions-core:${Versions.KOTEST}"
    const val KOTEST_FRAMEWORK_ENGINE = "io.kotest:kotest-framework-engine:${Versions.KOTEST}"
}

object JVMLibs {
//    const val KLOCK =  "com.soywiz.korlibs.klock:klock-jvm:${Versions.KLOCK}"
}

object JVMTestingLibs {
    const val JUNIT5 = "org.junit.jupiter:junit-jupiter:${Versions.JUNIT5}"
    const val MOCKK = "io.mockk:mockk:${Versions.MOCKK}"
    const val COROUTINES_TEST =  "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.COROUTINES}"
    const val KOTEST_FRAMEWORK_API = "io.kotest:kotest-framework-api-jvm:${Versions.KOTEST}"
    const val KOTEST_JUNIT5_RUNNER = "io.kotest:kotest-runner-junit5-jvm:${Versions.KOTEST}"
}

object ReactLibs {
    const val HTML_JS = "org.jetbrains.kotlinx:kotlinx-html-js:${Versions.KOTLINX_HTML_JS}"
    const val REACT = "org.jetbrains:kotlin-react:${Versions.REACT}-pre.125-kotlin-${PluginsVersions.KOTLIN}"
    const val REACT_ROUTER = "org.jetbrains:kotlin-react-router-dom:${Versions.REACT_ROUTER}-pre.125-kotlin-${PluginsVersions.KOTLIN}"
    const val REDUX = "org.jetbrains:kotlin-redux:${Versions.REDUX}-pre.125-kotlin-${PluginsVersions.KOTLIN}"
    const val REACT_REDUX = "org.jetbrains:kotlin-react-redux:${Versions.REACT_REDUX}-pre.125-kotlin-${PluginsVersions.KOTLIN}"
    const val REACT_DOM = "org.jetbrains:kotlin-react-dom:${Versions.REACT}-pre.125-kotlin-${PluginsVersions.KOTLIN}"
    const val STYLED = "org.jetbrains:kotlin-styled:5.2.0-pre.125-kotlin-${PluginsVersions.KOTLIN}"
    const val CSS_JS = "org.jetbrains:kotlin-css-js:1.0.0-pre.125-kotlin-${PluginsVersions.KOTLIN}"
    const val MUIRWIK = "com.ccfraser.muirwik:muirwik-components:${Versions.MUIRWIK}"

    const val KTOR_CLIENT_JS = "io.ktor:ktor-client-js:${Versions.KTOR}"
    const val KTOR_CLIENT_JSON = "io.ktor:ktor-client-json-js:${Versions.KTOR}"
    const val KTOR_CLIENT_SERIALIZATION = "io.ktor:ktor-client-serialization-js:${Versions.KTOR}"
}

object ReactTestingLibs {
    const val KOTEST_FRAMEWORK_API = "io.kotest:kotest-framework-api-js:${Versions.KOTEST}"
}

object KtorLibs {
    const val SERVER_CORE = "io.ktor:ktor-server-core:${Versions.KTOR}"
    const val SERVER_NETTY = "io.ktor:ktor-server-netty:${Versions.KTOR}"
    const val JWT = "io.ktor:ktor-auth-jwt:${Versions.KTOR}"
    const val WEB_SOCKETS = "io.ktor:ktor-websockets:${Versions.KTOR}"
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
    const val KOTLIN_JDK = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${PluginsVersions.KOTLIN}"

    const val APP_COMPAT = "androidx.appcompat:appcompat:${Versions.APP_COMPAT}"
    const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"
    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}"
    const val LIFECYCLE_VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE}"
    const val LIFECYCLE_LIVEDATA = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.LIFECYCLE}"
    const val LIFECYCLE_SAVEDSTATE = "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.LIFECYCLE}"
    const val LIFECYCLE_RUNTIME_KTX =  "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE}"
    const val LIFECYCLE_EXTENSTIONS =  "androidx.lifecycle:lifecycle-extensions:${Versions.LIFECYCLE}"
    const val FRAGMENT_KTX =  "androidx.fragment:fragment-ktx:${Versions.FRAGMENT_KTX}"

    const val COIL = "io.coil-kt:coil:1.0.0"

    const val KODEIN_DI_FRAMEWORK_ANDROID_X = "org.kodein.di:kodein-di-framework-android-x:${Versions.KODEIN}"

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

    const val AUTH0 = "com.auth0.android:auth0:1.25.0"
}

object AndroidTestingLibs {
    const val ANDROIDX_TEST_EXT_JUNIT = "androidx.test.ext:junit:${Versions.ANDROIDX_TEST}"
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_CORE}"
}