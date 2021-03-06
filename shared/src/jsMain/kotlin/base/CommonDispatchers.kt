package base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual object CommonDispatchers {

    actual val BackgroundDispatcher: CoroutineDispatcher
        get() = Dispatchers.Default

    actual val MainDispatcher: CoroutineDispatcher
        get() = Dispatchers.Main
}
