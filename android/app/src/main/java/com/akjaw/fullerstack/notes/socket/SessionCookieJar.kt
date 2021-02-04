package com.akjaw.fullerstack.notes.socket

import com.akjaw.fullerstack.helpers.logger.log
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import java.util.*


class SessionCookieJar : CookieJar {
    private var cookies: List<Cookie>? = null

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        url.log("Cookies url")
        cookies.log("Cookies cookies")
        val sessionCookie = cookies.firstOrNull { cookie ->
            cookie.name.toLowerCase(Locale.getDefault()) == "session"
        }
        if (sessionCookie != null) {
            "We got the cookie".log()
            this.cookies = listOf(sessionCookie)
        }
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return cookies ?: emptyList()
    }
}
