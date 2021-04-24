package server.composition

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton
import server.jwt.TokenParser
import server.logger.ApiLogger

val baseModule = DI.Module("baseModule") {
    bind<Config>() with singleton { ConfigFactory.load() }
    bind() from singleton { ApiLogger() }
    bind() from singleton {
        val config = instance<Config>()
        TokenParser(config.getString("ktor.domain"), instance())
    }
}
