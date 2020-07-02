package features.noteslist

import com.ccfraser.muirwik.components.list.mList
import com.ccfraser.muirwik.components.mCircularProgress
import com.soywiz.klock.DateFormat
import data.Note
import kotlinx.css.Align
import kotlinx.css.Display
import kotlinx.css.JustifyContent
import kotlinx.css.LinearDimension
import kotlinx.css.alignItems
import kotlinx.css.display
import kotlinx.css.justifyContent
import kotlinx.css.minHeight
import kotlinx.css.width
import react.RProps
import react.child
import react.functionalComponent
import styled.StyleSheet
import styled.css
import styled.styledDiv

interface NotesListProps : RProps {
    var isLoading: Boolean
    var notesList: Array<Note>
    var openEditor: (note: Note?) -> Unit
    var dateFormat: DateFormat
}

@Suppress("MagicNumber")
private object NotesListClasses : StyleSheet("NoteList", isStatic = true) {
    val root by css {
        minHeight = LinearDimension("400px")
    }
    val loading by css {
        display = Display.flex
        justifyContent = JustifyContent.center
        alignItems = Align.center
    }
    val list by css {
        width = LinearDimension("100%")
    }
}

val notesList = functionalComponent<NotesListProps> { props ->

    styledDiv {
        css(NotesListClasses.root)
        if (props.isLoading) {
            css(NotesListClasses.loading)
            mCircularProgress { }
        } else {
            child(addNoteButton) {
                attrs.onClick = props.openEditor
            }
            mList {
                css(NotesListClasses.list)
                props.notesList.forEach { note ->
                    child(notesListItem) {
                        attrs.note = note
                        attrs.dateFormat = props.dateFormat
                        attrs.onNoteClicked = props.openEditor
                    }
                }
            }
        }
    }
}
