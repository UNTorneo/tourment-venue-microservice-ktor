package com.nestorsgarzonc.features.photos.router

import com.nestorsgarzonc.features.photos.model.*
import com.nestorsgarzonc.features.photos.controller.PhotosController
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import org.koin.ktor.ext.inject

fun Routing.photosRouter() {
    route("/photo") {
        val controller by inject<PhotosController>()
        get() {
            val photoId = call.request.queryParameters["id"]?.toIntOrNull()
            if (photoId != null) {
                val photos = controller.getPhotoById(photoId) ?: return@get call.respond(
                    status = HttpStatusCode.NotFound,
                    mapOf("error" to "No se encontro la foto")
                )
                return@get call.respond(photos)
            }
            val courtId = call.request.queryParameters["courtId"]?.toIntOrNull()
            if (courtId != null) {
                val photos = controller.getPhotosByCourtId(courtId) ?: return@get call.respond(
                    status = HttpStatusCode.NotFound,
                    mapOf("error" to "No se encontro la foto")
                )
                return@get call.respond(photos)
            }
            val photos = controller.getAllPhotos()
            call.respond(photos)
        }
        post<AddPhoto> { photos ->
            val res = controller.createPhoto(photos) ?: return@post call.respond(
                mapOf("message" to "Creado exitosamente")
            )
            call.respond(
                status = HttpStatusCode.BadRequest,
                mapOf("error" to res.message)
            )
        }
        put<UpdatePhoto> { updatePhoto ->
            val photoId = call.request.queryParameters["id"]?.toIntOrNull()
            if (photoId != null) {
                val failure = controller.updatePhoto(photoId, updatePhoto) ?: return@put call.respond(
                    mapOf("message" to "Actualizado exitosamente")
                )
                return@put call.respond(
                    status = HttpStatusCode.BadRequest,
                    mapOf("error" to failure.message)
                )
            }
            call.respond(
                status = HttpStatusCode.BadRequest,
                mapOf("error" to "Falta el id de la foto")
            )
        }
        delete() {
            val photoId = call.request.queryParameters["id"]?.toIntOrNull()
            if (photoId != null) {
                val failure = controller.deletePhotoById(photoId) ?: return@delete call.respond(
                    mapOf("message" to "Eliminado exitosamente")
                )
                return@delete call.respond(
                    status = HttpStatusCode.BadRequest,
                    mapOf("error" to failure.message)
                )
            }
            call.respond(
                status = HttpStatusCode.BadRequest,
                mapOf("error" to "Falta el id de la foto")
            )
        }
    }
}