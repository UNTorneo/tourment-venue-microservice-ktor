package com.nestorsgarzonc.features.owner.router

import com.nestorsgarzonc.features.owner.model.*
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import org.koin.ktor.ext.inject

//BASE ROUTE: /owner/
fun Routing.ownerRouter() {

    route("/owner") {
        val ownerController by inject<com.nestorsgarzonc.features.owner.controller.OwnerController>()
        //Get owners
        get() {
            val ownerId = call.request.queryParameters["id"]?.toIntOrNull()
            if (ownerId != null) {
                val owner = ownerController.getOwnerById(ownerId) ?: return@get call.respond(
                    status = HttpStatusCode.NotFound,
                    mapOf("error" to "No se encontro el dueño")
                )
                return@get call.respond(owner)
            }
            val venueId = call.request.queryParameters["venueId"]?.toIntOrNull()
            if (venueId != null) {
                val owner = ownerController.getOwnerByVenueId(venueId) ?: return@get call.respond(
                    status = HttpStatusCode.NotFound,
                    mapOf("error" to "No se encontro un dueño asociado a la sede")
                )
                return@get call.respond(owner)
            }
            val owners = ownerController.getAllOwners()
            call.respond(owners)
        }
        //Delete an owner
        delete() {
            val ownerId = call.request.queryParameters["id"]?.toIntOrNull()
            if (ownerId != null) {
                val failure = ownerController.deleteOwnerById(ownerId) ?: return@delete call.respond(
                    mapOf("message" to "Eliminado exitosamente")
                )
                return@delete call.respond(
                    status = HttpStatusCode.BadRequest,
                    mapOf("error" to failure.message)
                )
            }
            call.respond(
                status = HttpStatusCode.BadRequest,
                mapOf("error" to "Falta el id del dueño")
            )
        }
        //Create an owner
        post<AddOwner> { owner ->
            val res = ownerController.createOwner(owner) ?: return@post call.respond(
                mapOf("message" to "Creado exitosamente")
            )
            call.respond(
                status = HttpStatusCode.BadRequest,
                mapOf("error" to res.message)
            )
        }
    }
}