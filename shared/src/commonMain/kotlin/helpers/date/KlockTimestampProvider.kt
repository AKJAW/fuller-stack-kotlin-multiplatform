package helpers.date

import com.soywiz.klock.DateTime

class KlockTimestampProvider : TimestampProvider {
    override fun now(): Long = DateTime.nowUnixLong()
}