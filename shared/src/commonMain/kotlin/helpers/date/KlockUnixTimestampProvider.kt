package helpers.date

import com.soywiz.klock.DateTime

class KlockUnixTimestampProvider : UnixTimestampProvider {
    override fun now(): Long = DateTime.nowUnixLong()
}
