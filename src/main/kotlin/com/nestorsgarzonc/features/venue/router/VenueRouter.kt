package com.nestorsgarzonc.features.venue.router

import com.nestorsgarzonc.features.venue.controller.VenueController
import com.nestorsgarzonc.features.venue.model.*
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import org.koin.ktor.ext.inject

fun Routing.venueRouter() {
    route("/venue") {
        val controller by inject<VenueController>()
        get() {
            val venueId = call.request.queryParameters["id"]?.toIntOrNull()
            if (venueId != null) {
                val venue = controller.getVenueById(venueId) ?: return@get call.respond(
                    status = HttpStatusCode.NotFound,
                    mapOf("error" to "No se encontro la sede")
                )
                return@get call.respond(venue)
            }
            val userId = call.request.queryParameters["userId"]
            if (userId != null) {
                val venue = controller.getVenueByUserId(userId) ?: return@get call.respond(
                    status = HttpStatusCode.NotFound,
                    mapOf("error" to "No se encontro la sede")
                )
                return@get call.respond(venue)
            }
            val venues = controller.getAllVenues()
            call.respond(venues)
        }
        post<AddVenue> { venue ->
            val res = controller.createVenue(venue) ?: return@post call.respond(
                mapOf("message" to "Creado exitosamente")
            )
            call.respond(
                status = HttpStatusCode.BadRequest,
                mapOf("error" to res.message)
            )
        }
        put<UpdateVenue> { updateVenue ->
            val venueId = call.request.queryParameters["id"]?.toIntOrNull()
            if (venueId != null) {
                val failure = controller.updateVenue(venueId, updateVenue) ?: return@put call.respond(
                    mapOf("message" to "Actualizado exitosamente")
                )
                return@put call.respond(
                    status = HttpStatusCode.BadRequest,
                    mapOf("error" to failure.message)
                )
            }
            call.respond(
                status = HttpStatusCode.BadRequest,
                mapOf("error" to "Falta el id de la sede")
            )
        }
        delete() {
            val venueId = call.request.queryParameters["id"]?.toIntOrNull()
            if (venueId != null) {
                val failure = controller.deleteVenueById(venueId) ?: return@delete call.respond(
                    mapOf("message" to "Eliminado exitosamente")
                )
                return@delete call.respond(
                    status = HttpStatusCode.BadRequest,
                    mapOf("error" to failure.message)
                )
            }
            call.respond(
                status = HttpStatusCode.BadRequest,
                mapOf("error" to "Falta el id de la sede")
            )
        }
    }
}