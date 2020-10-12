package store

import kotlinext.js.js
import redux.RAction
import redux.WrapperAction

interface RThunk : RAction {
    operator fun invoke(
        dispatch: (RAction) -> WrapperAction,
        getState: () -> AppState
    ): WrapperAction
}

// Credit to https://github.com/AltmanEA/KotlinExamples
fun rThunk() =
    applyMiddleware<AppState, RAction, WrapperAction, RAction, WrapperAction>(
        { store ->
            { next ->
                { action ->
                    if (action is RThunk) {
                        action(store::dispatch, store::getState)
                    } else {
                        next(action)
                    }
                }
            }
        }
    )

val nullAction = js {}.unsafeCast<WrapperAction>()
