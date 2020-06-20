import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
    id("de.mannodermaus.android-junit5")
}

android {
    compileSdkVersion(Versions.COMPILE_SDK_VERSION)

    defaultConfig {
        minSdkVersion(Versions.MIN_SDK_VERSION)
        targetSdkVersion(Versions.TARGET_SDK_VERSION)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
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
        setSourceCompatibility(JavaVersion.VERSION_1_8)
        setTargetCompatibility(JavaVersion.VERSION_1_8)
    }
}

dependencies {
    implementation(project(":shared"))

    // kotlin jdk
    implementation(AndroidLibs.KOTLIN_JDK)

    // google android libs
    implementation(AndroidLibs.APP_COMPAT)
    implementation(AndroidLibs.MATERIAL)
    implementation(AndroidLibs.CONSTRAINT_LAYOUT)

    // dependency injection
    implementation(SharedLibs.KODEIN_DI)
    implementation(AndroidLibs.KODEIN_DI_FRAMEWORK_ANDROID_X)

    // lifecycle
    implementation(AndroidLibs.LIFECYCLE_RUNTIME_KTX)

    // network
    implementation(AndroidLibs.COROUTINES_ANDROID)

    // date
    implementation(SharedLibs.KLOCK)

    testImplementation(JVMTestingLibs.JUNIT5)
    testImplementation(SharedTestingLibs.MOCKK)
    testImplementation(JVMTestingLibs.COROUTINES_TEST)
    androidTestImplementation(AndroidTestingLibs.ANDROIDX_TEST_EXT_JUNIT)
    androidTestImplementation(AndroidTestingLibs.ESPRESSO_CORE)
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
