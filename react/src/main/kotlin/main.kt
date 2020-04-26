import react.dom.h1
import react.dom.render
import kotlin.browser.document
import sample.hello

fun main() {
    render(document.getElementById("root")) {
        h1 {
            + hello()
        }
    }
}