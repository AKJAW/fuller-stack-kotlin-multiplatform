@file:JsModule("redux")
@file:JsNonModule

package store

import redux.Action
import redux.Enhancer
import redux.Reducer
import redux.Store


external fun <S, A, R> createStore(
    reducer: Reducer<S, A>,
    preloadedState: S,
    enhancer: Enhancer<S, Action, Action, A, R>
): Store<S, A, R>

external fun <A, T1, R> compose(function1: (T1) -> R, function2: (A) -> T1): (A) -> R

external fun <A, T1, R> compose(function1: (T1) -> R, function2: (A) -> T1, function3: (A) -> T1): (A) -> R