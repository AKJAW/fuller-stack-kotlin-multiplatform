package com.akjaw.fullerstack.composition.modules

import com.akjaw.fullerstack.notes.socket.SocketWrapper
import okhttp3.Request
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val socketModule = DI.Module("socketModule") {
    bind("socketRequest") from singleton {
        Request.Builder()
        .url("https://fuller-stack-ktor.herokuapp.com/notes/ws")
        .build()
    }
    bind() from singleton { SocketWrapper(instance(), instance(), instance("socketRequest")) }
}
