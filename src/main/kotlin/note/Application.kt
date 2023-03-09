package note

import io.ktor.server.application.*
import note.plugins.*

fun main(args: Array<String>) = io.ktor.server.cio.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {

    DatabaseFactory.init(environment.config)
    configureSerialization()
    configureMonitoring()
    configureHTTP()
    configureRouting()
}
