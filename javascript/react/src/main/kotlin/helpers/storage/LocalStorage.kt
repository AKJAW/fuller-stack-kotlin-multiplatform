package helpers.storage

import kotlin.browser.window

class LocalStorage : Storage {

    override fun getString(key: String): String? {
        return window.localStorage.getItem(key)
    }

    override fun setString(key: String, value: String) {
        window.localStorage.setItem(key, value)
    }
}
