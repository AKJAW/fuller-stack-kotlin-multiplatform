package components.counterbutton

import dependencyinjection.KodeinEntry
import kotlinx.html.js.onClickFunction
import org.kodein.di.erased.instance
import react.RBuilder
import react.RComponent
import react.ReactElement
import react.dom.button
import react.dom.h1
import react.dom.p
import react.setState
import sample.Counter

class CounterButton : RComponent<CounterButtonProps, CounterButtonState>() {
    private val counter: Counter by KodeinEntry.kodein.instance<Counter>()

    override fun RBuilder.render() {
        h1 {
            + props.title
        }
        p {
            val text = state.count ?: "AA"
            + text
        }
        button {
            + "Click"
            attrs {
                onClickFunction = {
                    setState {
                        count = counter.getAndIncrement()
                    }
                }
            }
        }
    }
}

fun RBuilder.counterButton(handler: CounterButtonProps.() -> Unit): ReactElement {
    return child(CounterButton::class) {
        this.attrs(handler)
    }
}
