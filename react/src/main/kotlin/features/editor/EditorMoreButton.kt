package features.editor

import com.ccfraser.muirwik.components.button.MIconButtonSize
import com.ccfraser.muirwik.components.button.mIconButton
import com.ccfraser.muirwik.components.menu.mMenu
import com.ccfraser.muirwik.components.menu.mMenuItemWithIcon
import kotlinx.css.LinearDimension
import kotlinx.css.padding
import org.w3c.dom.Node
import org.w3c.dom.events.Event
import react.RProps
import react.functionalComponent
import react.useState
import styled.StyleSheet
import styled.css
import styled.styledDiv

@Suppress("MagicNumber")
private object EditorMoreButtonClasses : StyleSheet("NoteEditor", isStatic = true) {
    val button by css {
        padding(LinearDimension("0"))
    }
}

val editorMoreButton = functionalComponent<RProps> {

    val (isMenuShown, setIsMenuShown) = useState(false)
    val (anchorEl, setAnchorEl) = useState<Node?>(null)

    styledDiv {
        mIconButton("more_vert", size = MIconButtonSize.medium, onClick = { event: Event ->
            setAnchorEl(event.currentTarget.asDynamic() as? Node)
            setIsMenuShown(isMenuShown.not())
        }) {
            css(EditorMoreButtonClasses.button)
        }
        mMenu(isMenuShown, anchorElement = anchorEl, onClose = { _, reason -> setIsMenuShown(false) }) {
            mMenuItemWithIcon("delete", "Delete", onClick = {
                setAnchorEl(null)
                setIsMenuShown(false)
            })
        }
    }
}