package features.noteslist

import com.ccfraser.muirwik.components.button.MButtonVariant
import com.ccfraser.muirwik.components.button.mButton
import data.Note
import kotlinx.css.LinearDimension
import kotlinx.css.width
import react.RProps
import react.functionalComponent
import styled.StyleSheet
import styled.css

interface AddNoteButtonProps : RProps {
    var onClick: (note: Note?) -> Unit
}

@Suppress("MagicNumber")
private object AddNoteButtonClasses : StyleSheet("AddNoteButtonClasses", isStatic = true) {
    val root by css {
        width = LinearDimension("100%")
    }
}

val addNoteButton = functionalComponent<AddNoteButtonProps> { props ->
    mButton(caption = "Add a new note", variant = MButtonVariant.outlined, onClick = { props.onClick(null) }) {
        css(AddNoteButtonClasses.root)
    }
}
