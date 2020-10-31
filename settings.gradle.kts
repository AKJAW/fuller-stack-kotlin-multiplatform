rootProject.name = "fuller_stack"

enableFeaturePreview("GRADLE_METADATA")
include(
    "ktor",
    "shared",
    "android:app",
    "android:authentication",
    "android:framework",
    "javascript:react",
    "javascript:spa-authentication",
    "javascript:spa-persistance"
)
