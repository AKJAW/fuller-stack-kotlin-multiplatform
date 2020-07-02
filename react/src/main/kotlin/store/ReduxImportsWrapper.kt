@file:JsModule("redux")
@file:JsNonModule

package store

import redux.Action
import redux.Enhancer
import redux.Middleware
import redux.Reducer
import redux.Store

external fun <S, A, R> createStore(
    reducer: Reducer<S, A>,
    preloadedState: S,
    enhancer: Enhancer<S, Action, Action, A, R>
): Store<S, A, R>

external fun <A, T1, T2, R> compose(function1: (T2) -> R, function2: (T1) -> T2, function3: (A) -> T1): (A) -> R

external fun <S, A1, R1, A2, R2> applyMiddleware(
    vararg middlewares: Middleware<S, A1, R1, A2, R2>
): Enhancer<S, A1, R1, A2, R2>
