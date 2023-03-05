package com.nestorsgarzonc.plugins
import com.nestorsgarzonc.features.owner.router.ownerRouter
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
        route("/owner") {
            ownerRouter()
        }
        //route("/customer"){
        //    get{
        //        if (customerStorage.isNotEmpty()) {
        //            call.respond(customerStorage)
        //        } else {
        //            call.respond(
        //                Customer(
        //                "id",
        //                    "S",
        //                    "G",
        //                    "s@g.com"
        //            )
        //            )
        //        }
        //    }
        //    get("{id?}") {
        //        val id = call.parameters["id"] ?: return@get call.respondText(
        //            "Missing id",
        //            status = HttpStatusCode.BadRequest
        //        )
        //        val customer =
        //            customerStorage.find { it.id == id } ?: return@get call.respondText(
        //                "No customer with id $id",
        //                status = HttpStatusCode.NotFound
        //            )
        //        call.respond(customer)
        //    }
        //}
    }
}