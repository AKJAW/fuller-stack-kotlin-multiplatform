
import com.ccfraser.muirwik.components.MGridSize
import com.ccfraser.muirwik.components.MGridSpacing
import com.ccfraser.muirwik.components.mContainer
import com.ccfraser.muirwik.components.mGridContainer
import com.ccfraser.muirwik.components.mGridItem
import com.ccfraser.muirwik.components.styles.Breakpoint
import features.noteeditor.noteEditorContainer
import features.noteslist.notesListContainer
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState

class App : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        mContainer(maxWidth = Breakpoint.lg) {
            mGridContainer(spacing = MGridSpacing.spacing2) {
                mGridItem(xs = MGridSize.cells12, md = MGridSize.cells6) {
                    notesListContainer { }
                }
                mGridItem(xs = MGridSize.cells12, md = MGridSize.cells6) {
                    noteEditorContainer { }
                }
            }
        }
    }
}
