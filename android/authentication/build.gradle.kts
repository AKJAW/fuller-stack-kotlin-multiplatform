plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
    id("de.mannodermaus.android-junit5")
}

android {
    defaultConfig {
        minSdkVersion(Versions.MIN_SDK_VERSION)
        targetSdkVersion(Versions.TARGET_SDK_VERSION)
    }
    compileSdkVersion(Versions.COMPILE_SDK_VERSION)
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    defaultConfig {
        manifestPlaceholders = mapOf(
            "auth0Domain" to "@string/com_auth0_domain",
            "auth0Scheme" to "@string/com_auth0_schema"
        )
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(project(":android:framework"))

    implementation(AndroidLibs.KOTLIN_JDK)

    implementation(AndroidLibs.AUTH0)

    implementation(AndroidLibs.APP_COMPAT)
    implementation(AndroidLibs.MATERIAL)
    implementation(AndroidLibs.CONSTRAINT_LAYOUT)
    implementation(AndroidLibs.LIFECYCLE_RUNTIME_KTX)
    implementation(AndroidLibs.LIFECYCLE_EXTENSTIONS)

    implementation(SharedLibs.COROUTINES_CORE)

    implementation(SharedLibs.KODEIN_DI)
    implementation(AndroidLibs.KODEIN_DI_FRAMEWORK_ANDROID_X)

    testImplementation(JVMTestingLibs.JUNIT5)
    testImplementation(JVMTestingLibs.MOCKK)
    testImplementation(JVMTestingLibs.COROUTINES_TEST)
}
