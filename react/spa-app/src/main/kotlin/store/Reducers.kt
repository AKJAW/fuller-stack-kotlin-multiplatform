package store

import features.editor.NoteEditorSlice
import features.list.NotesListSlice
import features.settings.SettingsSlice
import redux.Reducer
import redux.combineReducers
import kotlin.reflect.KProperty1

fun combinedReducers() = combineReducersInferred(
    mapOf(
        AppState::notesListState to NotesListSlice::reducer,
        AppState::noteEditorState to NoteEditorSlice::reducer,
        AppState::settingsState to SettingsSlice::reducer,
    )
)

// credit https://github.com/JetBrains/kotlin-wrappers/blob/master/kotlin-redux/README.md
fun <S, A, R> combineReducersInferred(reducers: Map<KProperty1<S, R>, Reducer<*, A>>): Reducer<S, A> {
    return combineReducers(reducers.mapKeys { it.key.name })
}
