package features.editor.more

import com.ccfraser.muirwik.components.MColor
import com.ccfraser.muirwik.components.button.mButton
import com.ccfraser.muirwik.components.dialog.mDialog
import com.ccfraser.muirwik.components.dialog.mDialogActions
import com.ccfraser.muirwik.components.dialog.mDialogTitle
import com.ccfraser.muirwik.components.menu.mMenuItemWithIcon
import react.RProps
import react.functionalComponent
import react.useState
import styled.styledDiv

interface DeleteNoteButtonProps : RProps {
    var onDeleteClicked: () -> Unit
}

val deleteNoteButton = functionalComponent<DeleteNoteButtonProps> { props ->

    val (isDialogShown, setIsDialogShown) = useState(false)

    styledDiv {
        mMenuItemWithIcon("delete", "Delete", onClick = { setIsDialogShown(true) })
        mDialog(isDialogShown, onClose = { _, _ -> setIsDialogShown(false) }) {
            mDialogTitle("Are you sure you want to delete this note?")
            mDialogActions {
                mButton("Cancel", MColor.primary, onClick = { setIsDialogShown(false) })
                mButton("Yes", MColor.primary, onClick = { props.onDeleteClicked() })
            }
        }
    }
}
