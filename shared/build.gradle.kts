plugins {
    kotlin("multiplatform")
}

kotlin {
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
                implementation(SharedLibs.KODEIN_DI_ERASED)
            }
        }

        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation(SharedLibs.COROUTINES_COMMON)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(SharedLibs.COROUTINES_COMMON)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation(AndroidLibs.COROUTINES_ANDROID)
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
                implementation(SharedLibs.COROUTINES_COMMON)
                implementation(JVMTestingLibs.COROUTINES_TEST)
            }
        }

        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))
                implementation(ReactLibs.COROUTINES_JS)
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
                implementation(SharedLibs.COROUTINES_COMMON)
            }
        }
    }
}
