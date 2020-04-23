package sample

import kotlin.test.Test
import kotlin.test.assertTrue

class SampleTests {
    @Test
    fun testMe() {
        println(Platform.name)
        assertTrue(Sample().checkMe() > 0)
    }
}