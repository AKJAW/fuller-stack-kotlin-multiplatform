package base

import kotlinx.coroutines.CoroutineDispatcher

expect object CommonDispatchers {

    val BackgroundDispatcher: CoroutineDispatcher

    val MainDispatcher: CoroutineDispatcher
}
