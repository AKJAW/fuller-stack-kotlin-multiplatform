import react.RBuilder
import react.RComponent
import react.RErrorInfo
import react.RProps
import react.RState
import react.ReactElement
import react.dom.h1

interface ErrorBoundaryState: RState {
    var hasError: Boolean?
}

class ErrorBoundary : RComponent<RProps, ErrorBoundaryState>() {

    override fun componentDidCatch(error: Throwable, info: RErrorInfo) {
        console.error(error)
        console.error(info)
    }

    override fun RBuilder.render() {
        if(state.hasError == true){
            h1 { + "Something went wrong" }
        } else {
            props.children()
        }
    }
}

fun RBuilder.errorBoundary(handler: RProps.() -> Unit): ReactElement {
    return child(ErrorBoundary::class) {
        this.attrs(handler)
    }
}
