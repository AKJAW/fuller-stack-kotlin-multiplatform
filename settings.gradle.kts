rootProject.name = "fuller_stack"

pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "kotlin-multiplatform") {
                useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${requested.version}")
            }
        }
    }
}

include(
    "shared",
    "ktor",
    "android:app",
    "android:authentication",
    "android:framework",
    "javascript:react",
    "javascript:spa-authentication",
    "javascript:spa-persistance"
)
