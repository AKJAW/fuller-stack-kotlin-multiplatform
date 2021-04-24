package com.akjaw.framework.view

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView

inline fun TextView.doAfterDistinctTextChange(
    crossinline afterTextChanged: (string: String) -> Unit = {}
): TextWatcher {
    val textWatcher = object : TextWatcher {
        private var previousText = ""

        override fun afterTextChanged(s: Editable?) {
            val text = s?.toString()
            if (text != null && text != previousText) {
                previousText = text
                afterTextChanged.invoke(text)
            }
        }

        override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {
            /* Empty */
        }

        override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
            /* Empty */
        }
    }

    addTextChangedListener(textWatcher)

    return textWatcher
}
