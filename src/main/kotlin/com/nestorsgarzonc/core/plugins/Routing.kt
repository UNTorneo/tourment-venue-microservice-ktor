package com.nestorsgarzonc.core.plugins
import com.nestorsgarzonc.features.court.router.courtRouter
import com.nestorsgarzonc.features.owner.router.ownerRouter
import com.nestorsgarzonc.features.venue.router.venueRouter
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.resources.Resources
import io.ktor.server.application.*

fun Application.configureRouting() {
    install(Resources)
    routing {
        get("/") {
            call.respond(mapOf("message" to "I'm Working :v"))
        }
        ownerRouter()
        venueRouter()
        courtRouter()
    }
}