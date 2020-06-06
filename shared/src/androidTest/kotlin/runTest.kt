import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain

@ExperimentalCoroutinesApi
actual fun <T> runTest(block: suspend () -> T) {
    Dispatchers.setMain(Dispatchers.Default)
    runBlocking { block() }
}
