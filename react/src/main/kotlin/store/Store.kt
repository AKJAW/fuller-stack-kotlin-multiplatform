package store

import redux.RAction
import redux.rEnhancer

val myStore = createStore<State, RAction, dynamic>(
    combinedReducers(),
    State(),
    compose(
        rEnhancer(),
        js("if(window.__REDUX_DEVTOOLS_EXTENSION__ )window.__REDUX_DEVTOOLS_EXTENSION__ ();else(function(f){return f;});")
    )
)
