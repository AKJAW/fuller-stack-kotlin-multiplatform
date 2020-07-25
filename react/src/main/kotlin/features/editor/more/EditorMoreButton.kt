package features.editor.more

import com.ccfraser.muirwik.components.button.MIconButtonSize
import com.ccfraser.muirwik.components.button.mIconButton
import com.ccfraser.muirwik.components.menu.mMenu
import kotlinx.css.LinearDimension
import kotlinx.css.padding
import org.w3c.dom.Node
import org.w3c.dom.events.Event
import react.RProps
import react.child
import react.functionalComponent
import react.useState
import styled.StyleSheet
import styled.css
import styled.styledDiv

interface EditorMoreButtonProps : RProps {
    var onDeleteClicked: () -> Unit
}

private object EditorMoreButtonClasses : StyleSheet("NoteEditor", isStatic = true) {
    val button by css {
        padding(LinearDimension("0"))
    }
}

val editorMoreButton = functionalComponent<EditorMoreButtonProps> { props ->

    val (isMenuShown, setIsMenuShown) = useState(false)
    val (anchorEl, setAnchorEl) = useState<Node?>(null)

    styledDiv {
        mIconButton(
            iconName = "more_vert",
            size = MIconButtonSize.medium,
            onClick = { event: Event ->
                setAnchorEl(event.currentTarget.asDynamic() as? Node)
                setIsMenuShown(isMenuShown.not())
            }
        ) {
            css(EditorMoreButtonClasses.button)
        }
        mMenu(isMenuShown, anchorElement = anchorEl, onClose = { _, reason -> setIsMenuShown(false) }) {
            child(deleteNoteButton) {
                attrs.onDeleteClicked = {
                    setIsMenuShown(false)
                    setAnchorEl(null)
                    props.onDeleteClicked()
                }
            }
        }
    }
}
