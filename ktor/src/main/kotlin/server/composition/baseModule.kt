package server.composition

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton
import server.logger.ApiLogger

val baseModule = DI.Module("baseModule") {
    bind() from singleton { ApiLogger() }
}
