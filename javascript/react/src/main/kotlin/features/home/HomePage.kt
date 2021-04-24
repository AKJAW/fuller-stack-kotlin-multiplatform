package features.home

import com.ccfraser.muirwik.components.MGridSize
import com.ccfraser.muirwik.components.MGridSpacing
import com.ccfraser.muirwik.components.mGridContainer
import com.ccfraser.muirwik.components.mGridItem
import composition.KodeinEntry
import feature.socket.ListenToSocketUpdates
import features.editor.noteEditorContainer
import features.list.notesListContainer
import org.kodein.di.instance
import react.RCleanup
import react.RProps
import react.functionalComponent
import react.useEffectWithCleanup

val homePage = functionalComponent<RProps> {
    val listenToSocketUpdates by KodeinEntry.di.instance<ListenToSocketUpdates>()
    val cleanUp: RCleanup = { listenToSocketUpdates.close() }

    useEffectWithCleanup(listOf()) {
        listenToSocketUpdates.listenToSocketChanges()
        cleanUp
    }
    mGridContainer(spacing = MGridSpacing.spacing2) {
        mGridItem(xs = MGridSize.cells12, md = MGridSize.cells6) {
            notesListContainer { }
        }
        mGridItem(xs = MGridSize.cells12, md = MGridSize.cells6) {
            noteEditorContainer { }
        }
    }
}
