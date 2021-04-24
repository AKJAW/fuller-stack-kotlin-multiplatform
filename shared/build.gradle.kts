plugins {
    kotlin("multiplatform")
}
kotlin {
    targets {
        jvm("android") {
            tasks.withType<Test> {
                useJUnitPlatform()
            }
            compilations.all {
                kotlinOptions {
                    jvmTarget = "1.8"
                    java {
                        sourceCompatibility = JavaVersion.VERSION_1_8
                        targetCompatibility = JavaVersion.VERSION_1_8
                    }
                }
            }
        }
        js {
            browser()
        }
    }
    detekt {
        input = files(
            "src/androidMain/kotlin", "src/androidTest/kotlin",
            "src/commonMain/kotlin", "src/commonTest/kotlin",
            "src/jsMain/kotlin", "src/jsTest/kotlin"
        )
    }

    sourceSets {
        all {
            dependencies {
                implementation(SharedLibs.KODEIN_DI)
            }
        }

        val commonMain by getting {
            dependencies {
                implementation(SharedLibs.COROUTINES_CORE)
                implementation(SharedLibs.KLOCK)
                implementation(SharedLibs.KOTLINX_SERIALIZATION)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(SharedTestingLibs.KOTEST_ASSERTIONS)
                implementation(SharedTestingLibs.KOTEST_FRAMEWORK_ENGINE)
                implementation(SharedLibs.COROUTINES_CORE)
            }
        }

        val androidMain by sourceSets.getting {
            dependencies {
                implementation(AndroidLibs.RETROFIT)
                implementation(AndroidLibs.ROOM_RUNTIME)
                implementation(AndroidLibs.ROOM_KTX)
            }
        }
        val androidTest by sourceSets.getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
                implementation(JVMTestingLibs.COROUTINES_TEST)
                implementation(JVMTestingLibs.KOTEST_FRAMEWORK_API)
                implementation(JVMTestingLibs.KOTEST_JUNIT5_RUNNER)
            }
        }

        val jsMain by sourceSets.getting {
            dependencies {
                implementation(ReactLibs.KTOR_CLIENT_JS)
            }
        }
        val jsTest by sourceSets.getting {
            dependencies {
                implementation(kotlin("test-js"))
                implementation(ReactTestingLibs.KOTEST_FRAMEWORK_API)
            }
        }
    }
}
