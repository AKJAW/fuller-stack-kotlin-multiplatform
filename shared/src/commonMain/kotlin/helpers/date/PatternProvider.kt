package helpers.date

import com.soywiz.klock.DateFormat
import helpers.storage.Storage
import kotlinx.coroutines.flow.Flow

interface PatternProvider {

    val patternFlow: Flow<DateFormat>
}
