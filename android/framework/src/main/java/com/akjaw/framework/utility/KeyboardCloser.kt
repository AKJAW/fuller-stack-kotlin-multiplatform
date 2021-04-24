package com.akjaw.framework.utility

import android.app.Activity
import android.view.inputmethod.InputMethodManager

class KeyboardCloser(
    private val activity: Activity
) {

    fun close() {
        val focusedView = activity.currentFocus ?: return
        val context = activity.baseContext ?: return
        val imm: InputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(focusedView.windowToken, 0)
    }
}
