package features.noteslist

import com.ccfraser.muirwik.components.list.mList
import com.ccfraser.muirwik.components.list.mListItem
import com.ccfraser.muirwik.components.list.mListItemText
import com.soywiz.klock.DateFormat
import data.Note
import kotlinx.css.Align
import kotlinx.css.Color
import kotlinx.css.LinearDimension
import kotlinx.css.alignSelf
import kotlinx.css.backgroundColor
import kotlinx.css.marginRight
import kotlinx.css.width
import react.RProps
import react.dom.span
import react.functionalComponent
import styled.StyleSheet
import styled.css
import styled.styledDiv

interface NotesListProps : RProps {
    var notesList: Array<Note>
    var onNoteClicked: (note: Note) -> Unit
    var dateFormat: DateFormat
}

private object Classes : StyleSheet("NoteList", isStatic = true) {
    val list by css {
        width = LinearDimension("100%")
    }
    val note by css {
//        boxShadow(Color.blacks, 0.px, 2.px, 1.px, (-1).px)
    }
    val colorBadge by css {
        width = LinearDimension("5px")
        alignSelf = Align.stretch
        marginRight = LinearDimension("5px")
    }
}

val notesList = functionalComponent<NotesListProps> { props ->

    styledDiv {
        mList {
            css(Classes.list)
            props.notesList.forEach { note ->
                mListItem(button = true, onClick = { props.onNoteClicked(Note("Teee")) }) {
                    css(Classes.note)
                    styledDiv {
                        css(Classes.colorBadge)
                        css {
                            backgroundColor = Color("#00b7ffc2")
                        }
                    }
                    mListItemText(note.title)
                    span {
                        + note.creationDate.format(props.dateFormat)
                    }
                }
            }
        }
    }
}
