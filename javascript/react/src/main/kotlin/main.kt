import com.ccfraser.muirwik.components.mThemeProvider
import com.ccfraser.muirwik.components.styles.mStylesProvider
import react.dom.render
import react.redux.provider
import store.myStore
import kotlin.browser.document
import kotlinx.browser.window

fun main() {
    render(document.getElementById("root")) {
//        errorBoundary {
        provider(myStore) {
            mStylesProvider("jss-insertion-point") {
                mThemeProvider {
                    Auth0Provider {
                        attrs.domain = AuthenticationConfig.domain
                        attrs.clientId = AuthenticationConfig.clientId
                        attrs.audience = AuthenticationConfig.audience
                        attrs.redirectUri = window.location.origin
                        attrs.onRedirectCallback = { }

                        child(App::class) {}
                    }
                }
            }
        }
//    }
    }
}
