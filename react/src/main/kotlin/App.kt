import components.counter_button.counterButton
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState

class App: RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        counterButton {
            title = "Props"
        }
    }
}