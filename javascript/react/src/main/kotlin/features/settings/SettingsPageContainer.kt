package features.settings

import helpers.date.NoteDateFormat
import react.RBuilder
import react.RClass
import react.RComponent
import react.RProps
import react.RState
import react.child
import react.invoke
import react.redux.rConnect
import redux.RAction
import redux.WrapperAction
import store.AppState

interface SettingsPageConnectedProps : RProps {
    var selectedNoteDateFormat: NoteDateFormat
    var listenToNoteDateFormatChanges: () -> Unit
    var changeNoteDateFormat: (noteDateFormat: NoteDateFormat) -> Unit
}

private interface StateProps : RProps {
    var selectedNoteDateFormat: NoteDateFormat
}

private interface DispatchProps : RProps {
    var listenToNoteDateFormatChanges: () -> Unit
    var changeNoteDateFormat: (noteDateFormat: NoteDateFormat) -> Unit
}

private class SettingsPageContainer(props: SettingsPageConnectedProps) : RComponent<SettingsPageConnectedProps, RState>(props) {

    override fun componentDidMount() {
        props.listenToNoteDateFormatChanges()
    }

    override fun RBuilder.render() {
        child(settingsPage) {
            attrs.selectedNoteDateFormat = props.selectedNoteDateFormat
            attrs.changeNoteDateFormat = props.changeNoteDateFormat
        }
    }
}

val settingsPageContainer: RClass<RProps> =
    rConnect<AppState, RAction, WrapperAction, RProps, StateProps, DispatchProps, SettingsPageConnectedProps>(
        { state, _ ->
            selectedNoteDateFormat = state.settingsState.selectedNoteDateFormat
        },
        { dispatch, _ ->
            listenToNoteDateFormatChanges = { dispatch(SettingsSlice.listenToNoteDateFormatChanges()) }
            changeNoteDateFormat = { dateFormat -> dispatch(SettingsSlice.changeNoteDateFormat(dateFormat)) }
        }
    )(SettingsPageContainer::class.js.unsafeCast<RClass<SettingsPageConnectedProps>>())
