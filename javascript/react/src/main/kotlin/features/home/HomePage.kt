package features.home

import com.ccfraser.muirwik.components.MGridSize
import com.ccfraser.muirwik.components.MGridSpacing
import com.ccfraser.muirwik.components.mGridContainer
import com.ccfraser.muirwik.components.mGridItem
import features.editor.noteEditorContainer
import features.list.notesListContainer
import react.RProps
import react.functionalComponent

val homePage = functionalComponent<RProps> {
    mGridContainer(spacing = MGridSpacing.spacing2) {
        mGridItem(xs = MGridSize.cells12, md = MGridSize.cells6) {
            notesListContainer { }
        }
        mGridItem(xs = MGridSize.cells12, md = MGridSize.cells6) {
            noteEditorContainer { }
        }
    }
}
