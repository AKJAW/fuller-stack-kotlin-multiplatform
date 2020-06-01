@file:Suppress("MatchingDeclarationName")
package store

import redux.RAction
import redux.WrapperAction
import redux.applyMiddleware

interface RThunk : RAction {
    operator fun invoke(
        dispatch: (RAction) -> WrapperAction,
        getState: () -> State
    ): WrapperAction
}

fun rThunk() =
    applyMiddleware<State, RAction, WrapperAction, RAction, WrapperAction>(
        {store ->
            {next ->
                {action ->
                    if(action is RThunk)
                        action(store::dispatch, store::getState)
                    else
                        next(action)
                }
            }
        }
    )