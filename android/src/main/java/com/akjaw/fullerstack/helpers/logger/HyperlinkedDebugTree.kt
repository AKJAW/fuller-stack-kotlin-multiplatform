package com.akjaw.fullerstack.helpers.logger

import timber.log.Timber

// Credit https://proandroiddev.com/android-logging-on-steroids-clickable-logs-with-location-info-de1a5c16e86f
class HyperlinkedDebugTree : Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String? {
        with(element) {
            return "($fileName:$lineNumber)$methodName()"
        }
    }
}
