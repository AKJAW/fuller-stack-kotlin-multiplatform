package features.list

import com.ccfraser.muirwik.components.list.mList
import com.ccfraser.muirwik.components.mCircularProgress
import feature.local.sort.SortProperty
import kotlinx.css.Align
import kotlinx.css.Display
import kotlinx.css.JustifyContent
import kotlinx.css.LinearDimension
import kotlinx.css.alignItems
import kotlinx.css.display
import kotlinx.css.justifyContent
import kotlinx.css.minHeight
import kotlinx.css.width
import model.Note
import react.RProps
import react.child
import react.functionalComponent
import styled.StyleSheet
import styled.css
import styled.styledDiv

interface NotesListProps : RProps {
    var isLoading: Boolean
    var notesList: Array<Note>
    var sortProperty: SortProperty
    var searchValue: String
    var openEditor: (note: Note?) -> Unit
    var changeSort: (sortProperty: SortProperty) -> Unit
    var changeSearchValue: (searchValue: String) -> Unit
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
            child(actionRow) {
                attrs.onAddNoteClick = props.openEditor
                attrs.sortProperty = props.sortProperty
                attrs.changeSort = props.changeSort
                attrs.searchValue = props.searchValue
                attrs.changeSearchValue = props.changeSearchValue
            }
            mList {
                css(NotesListClasses.list)
                props.notesList.forEach { note ->
                    child(notesListItem) {
                        attrs.note = note
                        attrs.onNoteClicked = props.openEditor
                    }
                }
            }
        }
    }
}
