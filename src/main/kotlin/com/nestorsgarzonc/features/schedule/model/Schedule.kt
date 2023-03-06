package com.nestorsgarzonc.features.schedule.model

import com.nestorsgarzonc.features.court.model.Courts
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.javatime.date

@Serializable
data class Schedule(
    val id: Int,
    val openHour: String,
    val closeHour: String,
    val weekDay: UInt,
    val courtId: Int,
    val price: ULong,
)

@Serializable
data class AddSchedule(
    val openHour: String,
    val closeHour: String,
    val weekDay: UInt,
    val courtId: Int,
    val price: ULong,
)

@Serializable
data class UpdateSchedule(
    val id: Int? = null,
    val openHour: String? = null,
    val closeHour: String? = null,
    val weekDay: UInt? = null,
    val courtId: Int? = null,
    val price: ULong? = null,
)

fun resultRowToSchedule(row: ResultRow) = Schedule(
    id = row[Schedules.id].value,
    courtId = row[Schedules.courtId].value,
    price = row[Schedules.price],
    weekDay = row[Schedules.weekDay],
    openHour = row[Schedules.openHour].toString(),
    closeHour = row[Schedules.closeHour].toString(),
)

object Schedules : IntIdTable() {
    val openHour = date("openHour")
    val closeHour = date("closeHour")
    val weekDay = uinteger("weekDay")
    val courtId = reference("courtId", Courts)
    val price = ulong("price")
}
