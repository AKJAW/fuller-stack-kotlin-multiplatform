package features.noteslist

import data.Note
import react.RProps
import react.dom.div
import react.functionalComponent

interface NotesListProps: RProps {
    var notesList: Array<Note>
}

val notesList = functionalComponent<NotesListProps> { props ->
    div {
        props.notesList.forEach { note ->
            div {
                + note.title
            }
        }
    }
}
