package features.list

import com.ccfraser.muirwik.components.button.MButtonVariant
import com.ccfraser.muirwik.components.button.MIconButtonSize
import com.ccfraser.muirwik.components.button.mButton
import com.ccfraser.muirwik.components.button.mIconButton
import com.ccfraser.muirwik.components.form.MFormControlVariant
import com.ccfraser.muirwik.components.input.mInput
import com.ccfraser.muirwik.components.mTextField
import com.ccfraser.muirwik.components.menu.mMenu
import com.ccfraser.muirwik.components.menu.mMenuItem
import com.ccfraser.muirwik.components.menu.mMenuItemWithIcon
import com.ccfraser.muirwik.components.targetInputValue
import feature.local.sort.SortDirection
import feature.local.sort.SortProperty
import feature.local.sort.SortType
import kotlinx.css.*
import model.Note
import org.w3c.dom.Node
import react.RProps
import react.functionalComponent
import react.useState
import styled.StyleSheet
import styled.css
import styled.styledDiv

interface ActionRowProps : RProps {
    var onAddNoteClick: (note: Note?) -> Unit
    var sortProperty: SortProperty
    var searchValue: String
    var changeSort: (sortProperty: SortProperty) -> Unit
    var changeSearchValue: (searchValue: String) -> Unit
}

@Suppress("MagicNumber")
private object ActionRowClasses : StyleSheet("ActionRowClasses", isStatic = true) {
    val root by css {
        display = Display.flex
        height = LinearDimension("45px")
    }
    val searchInput by css {
        width = LinearDimension("100%")
        margin = "0px"
        children("label") {
            top = LinearDimension("-4px")
        }
        children("div > input") {
            padding = "13px 14px"
        }
    }
    val addButton by css {
        width = LinearDimension("100%")
    }
    val menuButton by css {
        width = LinearDimension("100%")
        textTransform = TextTransform.none
        fontWeight = FontWeight.normal
        fontSize = LinearDimension("16px")
        justifyContent = JustifyContent.left
    }
    val menuLabel by css {
        fontSize = LinearDimension("14px")
        paddingBottom = LinearDimension("0px")
        paddingLeft = LinearDimension("10px")
        fontWeight = FontWeight.bold
    }
}

val actionRow = functionalComponent<ActionRowProps> { props ->

    val (isSearchShown, setIsSearchShown) = useState(false)
    val (isFilterShown, setIsFilterShown) = useState(false)
    val (anchorEl, setAnchorEl) = useState<Node?>(null)

    styledDiv {
        css(ActionRowClasses.root)
        if (isSearchShown) {
            mTextField(
                label = "Search",
                autoFocus = true,
                value = props.searchValue,
                variant = MFormControlVariant.outlined,
                onChange = { event -> props.changeSearchValue(event.targetInputValue) }
            ) {
                css(ActionRowClasses.searchInput)
            }
        } else {
            mButton(caption = "Add a new note", variant = MButtonVariant.outlined, onClick = { props.onAddNoteClick(null) }) {
                css(ActionRowClasses.addButton)
            }
        }
        if (isSearchShown) {
            mIconButton(
                iconName = "close",
                size = MIconButtonSize.medium,
                onClick = {
                    props.changeSearchValue("")
                    setIsSearchShown(false)
                }
            )
        } else {
            mIconButton(
                iconName = "search",
                size = MIconButtonSize.medium,
                onClick = { setIsSearchShown(true) }
            )
        }
        mIconButton(
            iconName = "sort_list",
            size = MIconButtonSize.medium,
            onClick = { event ->
                setAnchorEl(event.currentTarget.asDynamic() as? Node)
                setIsFilterShown(isFilterShown.not())
            }
        )
        mMenu(isFilterShown, anchorElement = anchorEl, onClose = { _, reason -> setIsFilterShown(false) }) {
            mMenuItem {
                + "Sort by"
                css(ActionRowClasses.menuLabel)
            }
            mMenuItem {
                css { padding = "0px" }
                mButton(
                    "Creation date",
                    disabled = props.sortProperty.type == SortType.CREATION_DATE,
                    onClick = { props.changeSort(props.sortProperty.copy(type = SortType.CREATION_DATE)) }
                ) {
                    css(ActionRowClasses.menuButton)
                }
            }
            mMenuItem {
                css { padding = "0px" }
                mButton(
                    "Name",
                    disabled = props.sortProperty.type == SortType.NAME,
                    onClick = { props.changeSort(props.sortProperty.copy(type = SortType.NAME)) }
                ) {
                    css(ActionRowClasses.menuButton)
                }
            }
            mMenuItem {
                + "Direction"
                css(ActionRowClasses.menuLabel)
            }
            mMenuItem {
                css { padding = "0px" }
                mButton(
                    "Ascending",
                    disabled = props.sortProperty.direction == SortDirection.ASCENDING,
                    onClick = { props.changeSort(props.sortProperty.copy(direction = SortDirection.ASCENDING)) }
                ) {
                    css(ActionRowClasses.menuButton)
                }
            }
            mMenuItem {
                css { padding = "0px" }
                mButton(
                    "Descending",
                    disabled = props.sortProperty.direction == SortDirection.DESCENDING,
                    onClick = { props.changeSort(props.sortProperty.copy(direction = SortDirection.DESCENDING)) }
                ) {
                    css(ActionRowClasses.menuButton)
                }
            }
        }
    }
}
