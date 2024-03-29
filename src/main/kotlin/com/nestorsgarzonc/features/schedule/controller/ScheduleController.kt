package com.nestorsgarzonc.features.schedule.controller

import com.nestorsgarzonc.core.failure.Failure
import com.nestorsgarzonc.core.plugins.DatabaseFactory.dbQuery
import com.nestorsgarzonc.features.schedule.model.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.time.LocalTime

class ScheduleController {
    suspend fun getAllSchedules(): List<Schedule> = dbQuery {
        try {
            Schedules.selectAll().map(::resultRowToSchedule)
        } catch (e: ExposedSQLException) {
            return@dbQuery emptyList<Schedule>()
        }
    }

    suspend fun getScheduleById(id: Int): Schedule? = dbQuery {
        try {
            Schedules
                .select { Schedules.id eq id }
                .map(::resultRowToSchedule)
                .singleOrNull()
        } catch (e: ExposedSQLException) {
            return@dbQuery null
        }
    }

    suspend fun getSchedulesByCourtId(id: Int): List<Schedule>? = dbQuery {
        try {
            Schedules
                .select { Schedules.courtId eq id }
                .map(::resultRowToSchedule)
        } catch (e: ExposedSQLException) {
            return@dbQuery null
        }
    }

    suspend fun createSchedule(schedule: AddSchedule): Failure? = dbQuery {
        try {
            val insertStatement = Schedules.insert {
                it[courtId] = schedule.courtId
                it[price] = schedule.price
                it[weekDay] = schedule.weekDay
                it[openHour] = LocalTime.parse(schedule.openHour)
                it[closeHour] = LocalTime.parse(schedule.closeHour)
            }
            insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToSchedule)
                ?: return@dbQuery Failure("No se pudo crear la cancha")
            return@dbQuery null
        } catch (e: ExposedSQLException) {
            return@dbQuery Failure("No se pudo crear la cancha")
        }
    }

    suspend fun updateSchedule(id: Int, schedule: UpdateSchedule): Failure? = dbQuery {
        try {
            val insertStatement = Schedules.update({ Schedules.id eq id }) {
                if (schedule.courtId != null) it[courtId] = schedule.courtId
                if (schedule.price != null) it[price] = schedule.price
                if (schedule.weekDay != null) it[weekDay] = schedule.weekDay
                if (schedule.openHour != null) it[openHour] = LocalTime.parse(schedule.openHour)
                if (schedule.closeHour != null) it[closeHour] = LocalTime.parse(schedule.closeHour)
            }
            if (insertStatement > 0) {
                return@dbQuery null
            }
            return@dbQuery Failure("Ha ocurrido un error actualizando la cancha")
        } catch (e: ExposedSQLException) {
            return@dbQuery Failure("Ha ocurrido un error actualizando la cancha")
        }
    }

    suspend fun deleteScheduleById(id: Int): Failure? = dbQuery {
        try {
            val res = Schedules.deleteWhere { Schedules.id eq id }
            if (res > 0) {
                return@dbQuery null
            }
            return@dbQuery Failure("Ha ocurrido un error eliminando la cancha")
        } catch (e: ExposedSQLException) {
            return@dbQuery Failure("Ha ocurrido un error eliminando la cancha")
        }
    }
}
