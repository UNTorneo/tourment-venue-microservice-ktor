package com.nestorsgarzonc.features.venue.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ResultRow

@Serializable
data class Venue(val id: Int, val location: String, val description: String, val isActive: Boolean)

@Serializable
data class AddVenue(val location: String, val description: String, val isActive: Boolean)
@Serializable
data class UpdateVenue(
    val location: String? = null,
    val description: String? = null,
    val isActive: Boolean? = null,
)

fun resultRowToVenue(row: ResultRow) = Venue(
    id = row[Venues.id].value,
    location = row[Venues.location],
    description = row[Venues.description],
    isActive = row[Venues.isActive],
)

object Venues : IntIdTable() {
    val location = varchar("location", length = 255)
    val description = varchar("description", length = 255)
    val isActive = bool("isActive").default(true)
}
