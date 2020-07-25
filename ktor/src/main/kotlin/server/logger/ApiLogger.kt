package server.logger

import org.slf4j.LoggerFactory

class ApiLogger {
    private val logger = LoggerFactory.getLogger("ApiLogger")

    fun log(tag: String, text: String) {
        logger.info("$tag: $text")
    }
}
