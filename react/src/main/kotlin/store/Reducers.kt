package store

import features.noteslist.NotesListSlice
import redux.Reducer
import redux.combineReducers
import kotlin.reflect.KProperty1

fun combinedReducers() = combineReducersInferred(
    mapOf(
        State::noteList to NotesListSlice::reducer
    )
)

//credit https://github.com/JetBrains/kotlin-wrappers/blob/master/kotlin-redux/README.md
fun <S, A, R> combineReducersInferred(reducers: Map<KProperty1<S, R>, Reducer<*, A>>): Reducer<S, A> {
    return combineReducers(reducers.mapKeys { it.key.name })
}
