
import com.ccfraser.muirwik.components.MGridSize
import com.ccfraser.muirwik.components.MGridSpacing
import com.ccfraser.muirwik.components.mContainer
import com.ccfraser.muirwik.components.mGridContainer
import com.ccfraser.muirwik.components.mGridItem
import com.ccfraser.muirwik.components.styles.Breakpoint
import features.editor.noteEditorContainer
import features.list.notesListContainer
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.child
import react.dom.div
import react.router.dom.browserRouter
import react.router.dom.route
import react.router.dom.switch

class App : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        mContainer(maxWidth = Breakpoint.lg) {
            child(authenticationWrapper) {
                browserRouter {
                    switch {
                        //TODO redirect 404 to root
                        route("/", exact = true) {
                            mGridContainer(spacing = MGridSpacing.spacing2) {
                                mGridItem(xs = MGridSize.cells12, md = MGridSize.cells6) {
                                    notesListContainer { }
                                }
                                mGridItem(xs = MGridSize.cells12, md = MGridSize.cells6) {
                                    noteEditorContainer { }
                                }
                            }
                        }
                        route("/profile") {
                            div {
                                + "User profile"
                            }
                        }
                    }
                }
            }
        }
    }
}
