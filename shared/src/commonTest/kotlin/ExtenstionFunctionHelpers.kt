import io.kotest.core.spec.style.scopes.FunSpecRootScope

fun <T> FunSpecRootScope.suspendingTest (name: String, block: suspend () -> T) {
    test(name) {
        runTest {
            block()
        }
    }
}
