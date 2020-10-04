import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
    id("de.mannodermaus.android-junit5")
}

repositories {
    maven { setUrl("https://jitpack.io") }
}

android {
    compileSdkVersion(Versions.COMPILE_SDK_VERSION)

    defaultConfig {
        applicationId = "com.akjaw.fullerstack"
        minSdkVersion(Versions.MIN_SDK_VERSION)
        targetSdkVersion(Versions.TARGET_SDK_VERSION)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
        manifestPlaceholders = mapOf("auth0Domain" to "", "auth0Scheme" to "") //TODO try to find a way to remove this from the main module
    }
    buildTypes {
        getByName("debug") {
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    packagingOptions {
        exclude("META-INF/kotlinx-serialization-runtime.kotlin_module")
    }
}

dependencies {
    implementation(project(":shared"))
    api(project(":android:authentication"))

    // debugging
    debugImplementation(AndroidLibs.LEAK_CANARY)
    implementation(AndroidLibs.TIMBER)

    // kotlin jdk
    implementation(AndroidLibs.KOTLIN_JDK)

    // google android libs
    implementation(AndroidLibs.APP_COMPAT)
    implementation(AndroidLibs.MATERIAL)
    implementation(AndroidLibs.CONSTRAINT_LAYOUT)
    implementation(AndroidLibs.LIFECYCLE_VIEWMODEL)
    implementation(AndroidLibs.LIFECYCLE_LIVEDATA)
    implementation(AndroidLibs.FRAGMENT_KTX)

    // dependency injection
    implementation(SharedLibs.KODEIN_DI)
    implementation(AndroidLibs.KODEIN_DI_FRAMEWORK_ANDROID_X)

    // lifecycle
    implementation(AndroidLibs.LIFECYCLE_RUNTIME_KTX)

    // network
    implementation(AndroidLibs.COROUTINES_ANDROID)
    implementation(AndroidLibs.OKHTTP)
    implementation(AndroidLibs.OKHTTP_LOGGING_INTERCEPTOR)
    implementation(AndroidLibs.RETROFIT)
    implementation(AndroidLibs.RETROFIT_KOTLINX_SERIALIZATION)
    implementation(SharedLibs.SERIALIZATION_RUNTIME_COMMON)

    // persistance
    implementation(AndroidLibs.ROOM_RUNTIME)
    kapt(AndroidLibs.ROOM_COMPILER)
    implementation(AndroidLibs.ROOM_KTX)

    // date
    implementation(SharedLibs.KLOCK)

    // navigation
    implementation(AndroidLibs.SIMPLE_STACK)
    implementation(AndroidLibs.SIMPLE_STACK_EXTENSIONS)

    testImplementation(JVMTestingLibs.JUNIT5)
    testImplementation(SharedTestingLibs.MOCKK)
    testImplementation(JVMTestingLibs.COROUTINES_TEST)
    androidTestImplementation(AndroidTestingLibs.ANDROIDX_TEST_EXT_JUNIT)
    androidTestImplementation(AndroidTestingLibs.ESPRESSO_CORE)
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
