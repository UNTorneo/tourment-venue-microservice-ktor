package com.nestorsgarzonc.features.schedule.router

import com.nestorsgarzonc.features.schedule.controller.ScheduleController
import com.nestorsgarzonc.features.schedule.model.*
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import org.koin.ktor.ext.inject

fun Routing.scheduleRouter() {
    route("/schedule") {
        val controller by inject<ScheduleController>()
        get() {
            val scheduleId = call.request.queryParameters["id"]?.toIntOrNull()
            if (scheduleId != null) {
                val schedule = controller.getScheduleById(scheduleId) ?: return@get call.respond(
                    status = HttpStatusCode.NotFound,
                    mapOf("error" to "No se encontrol calendario")
                )
                return@get call.respond(schedule)
            }
            val courtId = call.request.queryParameters["courtId"]?.toIntOrNull()
            if (courtId != null) {
                val schedule = controller.getSchedulesByCourtId(courtId) ?: return@get call.respond(
                    status = HttpStatusCode.NotFound,
                    mapOf("error" to "No se encontrol calendario")
                )
                return@get call.respond(schedule)
            }
            val schedules = controller.getAllSchedules()
            call.respond(schedules)
        }
        post<AddSchedule> { schedule ->
            val res = controller.createSchedule(schedule) ?: return@post call.respond(
                mapOf("message" to "Creado exitosamente")
            )
            call.respond(
                status = HttpStatusCode.BadRequest,
                mapOf("error" to res.message)
            )
        }
        put<UpdateSchedule> { updateSchedule ->
            val scheduleId = call.request.queryParameters["id"]?.toIntOrNull()
            if (scheduleId != null) {
                val failure = controller.updateSchedule(scheduleId, updateSchedule) ?: return@put call.respond(
                    mapOf("message" to "Actualizado exitosamente")
                )
                return@put call.respond(
                    status = HttpStatusCode.BadRequest,
                    mapOf("error" to failure.message)
                )
            }
            call.respond(
                status = HttpStatusCode.BadRequest,
                mapOf("error" to "Falta el id del calendario")
            )
        }
        delete() {
            val scheduleId = call.request.queryParameters["id"]?.toIntOrNull()
            if (scheduleId != null) {
                val failure = controller.deleteScheduleById(scheduleId) ?: return@delete call.respond(
                    mapOf("message" to "Eliminado exitosamente")
                )
                return@delete call.respond(
                    status = HttpStatusCode.BadRequest,
                    mapOf("error" to failure.message)
                )
            }
            call.respond(
                status = HttpStatusCode.BadRequest,
                mapOf("error" to "Falta el id del calendario")
            )
        }
    }
}