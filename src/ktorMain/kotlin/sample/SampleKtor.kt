package sample

actual class Sample {
    actual fun checkMe() = 68
}

actual object Platform {
    actual val name: String = "Ktor"
}