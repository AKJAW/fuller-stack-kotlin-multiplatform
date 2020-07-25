package com.akjaw.fullerstack.helpers.logger

import timber.log.Timber

inline fun Any?.log(prefix: String = "object:") = Timber.d("$prefix: ${toString()}")
