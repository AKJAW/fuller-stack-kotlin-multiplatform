package features.noteeditor

import com.ccfraser.muirwik.components.button.mButton
import com.ccfraser.muirwik.components.form.MFormControlVariant
import com.ccfraser.muirwik.components.mTextField
import com.ccfraser.muirwik.components.mTextFieldMultiLine
import com.ccfraser.muirwik.components.targetInputValue
import data.Note
import kotlinx.css.Display
import kotlinx.css.JustifyContent
import kotlinx.css.LinearDimension
import kotlinx.css.display
import kotlinx.css.justifyContent
import kotlinx.css.minHeight
import kotlinx.css.width
import react.RProps
import react.dom.div
import react.functionalComponent
import react.useState
import styled.StyleSheet
import styled.css
import styled.styledDiv

interface NoteEditorProps : RProps {
    var selectedNote: Note?
    var isTitleValid: Boolean
    var validateAndAddNote: (title: String, content: String) -> Unit
    var closeEditor: () -> Unit
}

@Suppress("MagicNumber")
private object Classes : StyleSheet("NoteEditor", isStatic = true) {
    val root by css {
        minHeight = LinearDimension("400px")
    }
    val actions by css {
        display = Display.flex
        justifyContent = JustifyContent.spaceBetween
    }
    val titleTextField by css {
        width = LinearDimension("100%")
    }
}

val noteEditor = functionalComponent<NoteEditorProps> { props ->

    val (title, setTitle) = useState(props.selectedNote?.title ?: "")
    val (content, setContent) = useState(props.selectedNote?.content ?: "")

    if(props.selectedNote != null) {
        styledDiv {
            css(Classes.root)
            styledDiv {
                css(Classes.actions)
                mButton("Cancel", onClick = { props.closeEditor() })
                mButton("Save", onClick = { props.validateAndAddNote(title, content) })
            }
            mTextField(label = "Title",
                value = title,
                variant = MFormControlVariant.outlined,
                error = props.isTitleValid.not(),
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
    } else {
        div {  }
    }
}
