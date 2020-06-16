package features.noteeditor

import com.ccfraser.muirwik.components.form.MFormControlVariant
import com.ccfraser.muirwik.components.mTextField
import com.ccfraser.muirwik.components.mTextFieldMultiLine
import com.ccfraser.muirwik.components.targetInputValue
import com.soywiz.klock.DateFormat
import data.Note
import kotlinx.css.LinearDimension
import kotlinx.css.minHeight
import kotlinx.css.width
import react.RProps
import react.functionalComponent
import react.useState
import styled.StyleSheet
import styled.css
import styled.styledDiv

interface NoteEditorProps : RProps {
    var isLoading: Boolean
    var notesList: Array<Note>
    var onNoteClicked: (note: Note) -> Unit
    var dateFormat: DateFormat
}

@Suppress("MagicNumber")
private object Classes : StyleSheet("NoteList", isStatic = true) {
    val root by css {
        minHeight = LinearDimension("400px")
    }
    val titleTextField by css {
        width = LinearDimension("100%")
    }
}

val noteEditor = functionalComponent<NoteEditorProps> { props ->
    val (title, setTitle) = useState("")
    val (content, setContent) = useState("")

    styledDiv {
        css(Classes.root)
        mTextField(label = "Title",
            value = title,
            variant = MFormControlVariant.outlined,
            onChange = { event -> setTitle(event.targetInputValue) }
        ) {
            css(Classes.titleTextField)
        }
        mTextFieldMultiLine(
            label = "Content",
            value = content,
            rowsMax = 10,
            variant = MFormControlVariant.outlined,
            onChange = { event -> setContent(event.targetInputValue) }
        ) {
            css(Classes.titleTextField)
        }
    }
}
