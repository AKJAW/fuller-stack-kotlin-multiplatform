import com.ccfraser.muirwik.components.mThemeProvider
import com.ccfraser.muirwik.components.styles.mStylesProvider
import react.dom.render
import react.redux.provider
import store.myStore
import kotlin.browser.document

fun main() {
    render(document.getElementById("root")) {
//        errorBoundary {
            provider(myStore) {
                mStylesProvider("jss-insertion-point") {
                    mThemeProvider {
                        child(App::class) {}
                    }
                }
            }
//        }
    }
}
