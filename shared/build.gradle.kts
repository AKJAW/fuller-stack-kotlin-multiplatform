plugins {
    id("org.jetbrains.kotlin.multiplatform")
}

kotlin {
    targets {
        jvm("android") {
            val main by compilations.getting {
                kotlinOptions {
                    jvmTarget = "1.8"
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
//                implementation(kotlin("stdlib-common"))
                implementation(SharedLibs.COROUTINES_CORE)
                implementation(SharedLibs.KLOCK)
                implementation(SharedLibs.SERIALIZATION_RUNTIME_COMMON)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(SharedLibs.COROUTINES_CORE)
            }
        }

        val androidMain by sourceSets.getting {
            dependencies {
//                implementation(kotlin("stdlib-jdk8"))
//                implementation(SharedLibs.COROUTINES_CORE)
//                implementation(JVMLibs.SERIALIZATION_RUNTIME_JVM)
                implementation(AndroidLibs.RETROFIT)
                implementation(AndroidLibs.ROOM_RUNTIME)
                implementation(AndroidLibs.ROOM_KTX)
            }
        }
        val androidTest by sourceSets.getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
//                implementation(SharedLibs.COROUTINES_CORE)
                implementation(JVMTestingLibs.COROUTINES_TEST)
            }
        }

        val jsMain by sourceSets.getting {
            dependencies {
//                implementation(kotlin("stdlib-js"))
                implementation(ReactLibs.KTOR_CLIENT_JS)
//                implementation(ReactLibs.COROUTINES_JS)
//                implementation(ReactLibs.SERIALIZATION_RUNTIME_JS)
            }
        }
        val jsTest by sourceSets.getting {
            dependencies {
                implementation(kotlin("test-js"))
//                implementation(SharedLibs.COROUTINES_CORE)
            }
        }
    }
}
