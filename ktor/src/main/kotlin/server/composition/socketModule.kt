package server.composition

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import server.socket.SocketServer

val socketModule = DI.Module("socketModule") {
    bind() from singleton { SocketServer(instance(), instance()) }
}
