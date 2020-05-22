package sample

actual class CounterImpl : Counter {
    private var count = 0

    override fun getAndIncrement(): String {
        count++
        return "JVM $count"
    }
}
