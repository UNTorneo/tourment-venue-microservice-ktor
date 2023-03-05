package com.nestorsgarzonc.features.venue.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table
@Serializable
data class Venue(val id: Int, val location: String, val description: String)

object Venues: IntIdTable() {
    val location = varchar("location", length = 255)
    val description = varchar("description", length = 255)
    val isActive= bool("isActive").default(true)
}
