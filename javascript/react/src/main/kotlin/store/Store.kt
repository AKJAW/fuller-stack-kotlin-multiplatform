package store

import redux.RAction
import redux.rEnhancer

@Suppress("MaxLineLength")
val myStore = createStore<AppState, RAction, dynamic>(
    combinedReducers(),
    AppState(),
    compose(
        rThunk(),
        rEnhancer(),
        js("if(window.__REDUX_DEVTOOLS_EXTENSION__ )window.__REDUX_DEVTOOLS_EXTENSION__ ();else(function(f){return f;});")
    )
)
