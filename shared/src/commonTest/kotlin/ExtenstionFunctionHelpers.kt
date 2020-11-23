import io.kotest.core.spec.style.scopes.FunSpecRootScope

inline fun <T> FunSpecRootScope.suspendingTest (name: String, crossinline block: suspend () -> T) {
    test(name) {
        runTest {
            block()
        }
    }
}
