package sample

import kotlin.test.Test
import kotlin.test.assertTrue

class SampleTestsKtor {
    @Test
    fun testHello() {
        println("aaas")
        assertTrue("Ktor" in hello())
    }
}