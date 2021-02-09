package features.settings

import com.ccfraser.muirwik.components.mSelect
import com.ccfraser.muirwik.components.menu.mMenuItem
import com.ccfraser.muirwik.components.targetValue
import com.soywiz.klock.DateFormat
import helpers.date.NoteDateFormat
import helpers.date.toDateFormat
import helpers.date.toNoteDateFormat
import profile.userProfile
import react.RProps
import react.child
import react.functionalComponent
import styled.styledDiv

interface NotesListProps : RProps {
    var selectedNoteDateFormat: NoteDateFormat
    var changeNoteDateFormat: (noteDateFormat: NoteDateFormat) -> Unit
}

val settingsPage = functionalComponent<NotesListProps> { props ->
    child(userProfile)
    console.log("props.selectedNoteDateFormat", props.selectedNoteDateFormat)
    styledDiv {
        mSelect(
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
