package helpers

object Do {
    inline infix fun <reified T> exhaustive(any: T?) = any
}
