package features.noteslist

import com.ccfraser.muirwik.components.list.mListItem
import com.ccfraser.muirwik.components.list.mListItemText
import com.soywiz.klock.DateFormat
import data.Note
import kotlinx.css.Align
import kotlinx.css.Color
import kotlinx.css.LinearDimension
import kotlinx.css.alignSelf
import kotlinx.css.backgroundColor
import kotlinx.css.boxShadow
import kotlinx.css.margin
import kotlinx.css.marginRight
import kotlinx.css.padding
import kotlinx.css.properties.BoxShadow
import kotlinx.css.px
import kotlinx.css.rgba
import kotlinx.css.width
import react.RProps
import react.dom.span
import react.functionalComponent
import styled.StyleSheet
import styled.css
import styled.styledDiv

interface NotesListItemProps : RProps {
    var note: Note
    var dateFormat: DateFormat
}

@Suppress("MagicNumber")
private object NotesListItemClasses : StyleSheet("NoteList", isStatic = true) {
    val note by css {
        boxShadow += BoxShadow(false, 0.px, 1.px, 3.px, 0.px, rgba(0,0,0,0.12))
        boxShadow += BoxShadow(false, 0.px, 1.px, 2.px, 0.px, rgba(0,0,0,0.24))
        margin = "5px 0"
        padding = "0px 8px 0 0"
    }
    val noteTitle by css {
        padding = "3px"
    }
    val colorBadge by css {
        width = LinearDimension("5px")
        alignSelf = Align.stretch
        marginRight = LinearDimension("5px")
    }
}

val notesListItem = functionalComponent<NotesListItemProps> { props ->
    val note = props.note
    styledDiv {
        mListItem(button = true) {
            css(NotesListItemClasses.note)
            styledDiv {
                css(NotesListItemClasses.colorBadge)
                css {
                    backgroundColor = Color("#00b7ffc2")
                }
            }
            mListItemText(note.title) {
                css(NotesListItemClasses.noteTitle)
            }
            span {
                +note.creationDate.format(props.dateFormat)
            }
        }
    }
}
