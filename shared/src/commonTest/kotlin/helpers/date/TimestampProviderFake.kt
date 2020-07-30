package helpers.date

class TimestampProviderFake: TimestampProvider {
    var timestamp: Long = -1
    override fun now(): Long = timestamp
}
