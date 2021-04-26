rootProject.name = "fuller_stack"

enableFeaturePreview("GRADLE_METADATA")
include(
    "shared",
    "ktor",
    "android:app",
    "android:authentication",
    "android:framework",
    "react:spa-app",
    "react:spa-authentication",
    "react:spa-persistance"
)
