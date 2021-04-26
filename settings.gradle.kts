rootProject.name = "fuller_stack"

enableFeaturePreview("GRADLE_METADATA")
include(
    "shared",
    "ktor",
    "android:app",
    "android:authentication",
    "android:framework",
    "javascript:spa-app",
    "javascript:spa-authentication",
    "javascript:spa-persistance"
)
