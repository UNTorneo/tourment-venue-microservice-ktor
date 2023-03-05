package com.nestorsgarzonc.features.schedule.model
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.date

@Serializable
data class Schedule(
    val id: Int,
    val openHour: String,
    val closeHour: String,
    val weekDay: String,
    val price: Long,
)

object Schedules: IntIdTable() {
    val openHour = date("openHour")
    val closeHour = date("closeHour")
    val weekDay = uinteger("weekDay")
    val price = ulong("price")
}
