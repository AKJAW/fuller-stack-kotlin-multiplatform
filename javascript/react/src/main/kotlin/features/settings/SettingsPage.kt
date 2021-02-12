package features.settings

import com.ccfraser.muirwik.components.mContainer
import com.ccfraser.muirwik.components.mSelect
import com.ccfraser.muirwik.components.mTypography
import com.ccfraser.muirwik.components.menu.mMenuItem
import com.ccfraser.muirwik.components.styles.Breakpoint
import com.ccfraser.muirwik.components.targetValue
import com.soywiz.klock.DateFormat
import helpers.date.NoteDateFormat
import helpers.date.toDateFormat
import helpers.date.toNoteDateFormat
import kotlinx.css.LinearDimension
import kotlinx.css.marginTop
import profile.userProfile
import react.RProps
import react.child
import react.functionalComponent
import styled.StyleSheet
import styled.css
import styled.styledDiv


@Suppress("MagicNumber")
private object SettingsPageClasses : StyleSheet("SettingsPage", isStatic = true) {
    val options by css {
        marginTop = LinearDimension("30px")
    }
}

interface NotesListProps : RProps {
    var selectedNoteDateFormat: NoteDateFormat
    var changeNoteDateFormat: (noteDateFormat: NoteDateFormat) -> Unit
}

val settingsPage = functionalComponent<NotesListProps> { props ->
    styledDiv {
        child(userProfile)
        console.log("props.selectedNoteDateFormat", props.selectedNoteDateFormat)
        mContainer(Breakpoint.xs) {
            css(SettingsPageClasses.options)
            mTypography("Date format:")
            mSelect(
                fullWidth = true,
                value = props.selectedNoteDateFormat.toDateFormat(),
                name = "Date format",
                onChange = { event, _ ->
                    console.log(event)
                    val value = event.targetValue as? String
                    if (value != null) {
                        val dateFormat = DateFormat(value)
                        props.changeNoteDateFormat(dateFormat.toNoteDateFormat())
                    }
                }
            ) {
                NoteDateFormat.values().map { noteDateFormat ->
                    mMenuItem(
                        primaryText = noteDateFormat.value,
                        value = noteDateFormat.value
                    )
                }
            }
        }
    }
}
