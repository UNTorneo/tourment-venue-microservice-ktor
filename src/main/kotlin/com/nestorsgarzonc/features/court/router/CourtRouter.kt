package com.nestorsgarzonc.features.court.router

import com.nestorsgarzonc.features.court.controller.CourtController
import com.nestorsgarzonc.features.court.model.*
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import org.koin.ktor.ext.inject

fun Routing.courtRouter() {
    route("/court") {
        val controller by inject<CourtController>()
        get {
            val courtId = call.request.queryParameters["id"]?.toIntOrNull()
            if (courtId != null) {
                val court = controller.getCourtById(courtId) ?: return@get call.respond(
                    status = HttpStatusCode.NotFound,
                    mapOf("error" to "No se encontro la cancha")
                )
                return@get call.respond(court)
            }
            val venueId = call.request.queryParameters["venueId"]?.toIntOrNull()
            if (venueId != null) {
                val court = controller.getCourtsByVenueId(venueId) ?: return@get call.respond(
                    status = HttpStatusCode.NotFound,
                    mapOf("error" to "No se encontro la cancha")
                )
                return@get call.respond(court)
            }
            val sportId = call.request.queryParameters["sportId"]
            if (sportId != null) {
                val court = controller.getCourtsBySportId(sportId) ?: return@get call.respond(
                    status = HttpStatusCode.NotFound,
                    mapOf("error" to "No se encontro la cancha")
                )
                return@get call.respond(court)
            }
            val courts = controller.getAllCourts()
            call.respond(courts)
        }
        post<AddCourt> { court ->
            val res = controller.createCourt(court) ?: return@post call.respond(
                mapOf("message" to "Creado exitosamente")
            )
            call.respond(
                status = HttpStatusCode.BadRequest,
                mapOf("error" to res.message)
            )
        }
        put<UpdateCourt> { updateCourt ->
            val courtId = call.request.queryParameters["id"]?.toIntOrNull()
            if (courtId != null) {
                val failure = controller.updateCourt(courtId, updateCourt) ?: return@put call.respond(
                    mapOf("message" to "Actualizado exitosamente")
                )
                return@put call.respond(
                    status = HttpStatusCode.BadRequest,
                    mapOf("error" to failure.message)
                )
            }
            call.respond(
                status = HttpStatusCode.BadRequest,
                mapOf("error" to "Falta el id de la cancha")
            )
        }
        delete {
            val courtId = call.request.queryParameters["id"]?.toIntOrNull()
            if (courtId != null) {
                val failure = controller.deleteCourtById(courtId) ?: return@delete call.respond(
                    mapOf("message" to "Eliminado exitosamente")
                )
                return@delete call.respond(
                    status = HttpStatusCode.BadRequest,
                    mapOf("error" to failure.message)
                )
            }
            call.respond(
                status = HttpStatusCode.BadRequest,
                mapOf("error" to "Falta el id de la cancha")
            )
        }
    }
}