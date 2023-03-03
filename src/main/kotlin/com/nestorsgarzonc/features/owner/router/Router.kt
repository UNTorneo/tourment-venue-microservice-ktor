package com.nestorsgarzonc.features.owner.router
import com.nestorsgarzonc.features.owner.models.Owner
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*

//BASE ROUTE: /owner/
fun Route.ownerRouter() {
    //Get all owners
    get("all/{venueId}"){
        val venueId = call.parameters["venueId"] ?: return@get call.respond(
            status = HttpStatusCode.BadRequest,
            mapOf("error" to "Oops, falta el id de la sede."),
        )
        call.respond<List<Owner>>(emptyList())
    }
    //Delete an owner
    delete("{ownerId}"){
        val ownerId = call.parameters["ownerId"] ?: return@delete call.respond(
            status = HttpStatusCode.BadRequest,
            mapOf("error" to "Oops, falta el id del usuario."),
        )
        call.respond<List<Owner>>(emptyList())
    }
    //Create an owner
    post<Owner>{ owner->
        print(owner)
        call.respond(owner)
    }
}