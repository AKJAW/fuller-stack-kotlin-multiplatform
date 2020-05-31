
import features.noteslist.notesListContainer
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div

class App : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
//        mCssBaseline() TODO is this necessary
        div {
            notesListContainer { }
        }
    }
}
