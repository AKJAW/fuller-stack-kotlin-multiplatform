package com.akjaw.fullerstack.helpers.logger

import timber.log.Timber

//TODO move this extenstion to common actual/expect
inline fun Any?.log(prefix: String = "object:") = Timber.d("$prefix: ${toString()}")
