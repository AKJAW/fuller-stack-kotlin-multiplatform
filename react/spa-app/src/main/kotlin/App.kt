
import com.ccfraser.muirwik.components.mContainer
import com.ccfraser.muirwik.components.styles.Breakpoint
import composition.KodeinEntry
import features.home.homePage
import features.settings.settingsPageContainer
import org.kodein.di.instance
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.child
import react.router.dom.browserRouter
import react.router.dom.route
import react.router.dom.switch

class App : RComponent<RProps, RState>() {
    private val tokenProvider by KodeinEntry.di.instance<TokenProvider>()

    override fun RBuilder.render() {
        child(authenticationWrapper) {
            attrs.tokenProvider = tokenProvider
            browserRouter {
                child(appBar)
                mContainer(maxWidth = Breakpoint.lg) {
                switch {
                    //TODO redirect 404 to root
                        route("/", exact = true) {
                            child(homePage)
                        }
                        route("/profile") {
                            settingsPageContainer {}
                        }
                    }
                }
            }
        }
    }
}
