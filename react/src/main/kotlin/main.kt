import react.dom.render
import react.redux.provider
import store.myStore
import kotlin.browser.document

fun main() {
    render(document.getElementById("root")) {
        provider(myStore){
            console.log(myStore)
            child(App::class) {}
        }
    }
}
