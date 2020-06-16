package features.noteslist

import com.ccfraser.muirwik.components.button.MButtonVariant
import com.ccfraser.muirwik.components.button.mButton
import com.soywiz.klock.DateFormat
import data.Note
import kotlinx.css.LinearDimension
import kotlinx.css.width
import react.RProps
import react.functionalComponent
import styled.StyleSheet
import styled.css

interface AddNoteButtonProps : RProps {
    var note: Note
    var dateFormat: DateFormat
}

@Suppress("MagicNumber")
private object AddNoteButtonClasses : StyleSheet("AddNoteButtonClasses", isStatic = true) {
    val root by css {
        width = LinearDimension("100%")
    }
}

val addNoteButton = functionalComponent<AddNoteButtonProps> { props ->
    mButton(caption = "Add a new note", variant = MButtonVariant.outlined) {
        css(AddNoteButtonClasses.root)
    }
}
