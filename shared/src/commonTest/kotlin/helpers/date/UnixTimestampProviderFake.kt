package helpers.date

class UnixTimestampProviderFake : UnixTimestampProvider {
    var timestamp: Long = -1
    override fun now(): Long = timestamp
}
