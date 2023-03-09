package note.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import note.NewTopic
import note.WikiService

fun Application.configureRouting() {

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }

    routing {

        get("/") {
            call.respondText("Hello World!")
        }

        post("/topic") {

            val newTopic = call.receive<NewTopic>()
            call.respond(
                HttpStatusCode.Created,
                WikiService.addTopic(newTopic)

            )
        }

        get("/topics") {
            call.respond(
                HttpStatusCode.OK,
                WikiService.getTopics()
            )
        }

        get("/topics/{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid id")
            val topic = WikiService.getTopic(id)
            if (topic == null) {
                call.respond(HttpStatusCode.NotFound)
            } else
                call.respond(topic)
        }

        // Static plugin. Try to access `/static/index.html`
        static("/static") {
            resources("static")
        }
    }
}
