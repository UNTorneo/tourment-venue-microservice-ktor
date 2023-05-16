package com.nestorsgarzonc.features.court.model

import com.nestorsgarzonc.features.venue.model.Venues
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ResultRow

@Serializable
data class Court(
    val id: Int,
    val venueId: Int,
    val sportId: String,
    val isActive: Boolean,
    val name: String,
)

@Serializable
data class AddCourt(
    val venueId: Int,
    val sportId: String,
    val isActive: Boolean,
    val name: String,
)

@Serializable
data class UpdateCourt(
    val venueId: Int? = null,
    val sportId: String? = null,
    val isActive: Boolean? = null,
    val name: String? = null,
)

fun resultRowToCourt(row: ResultRow) = Court(
    sportId = row[Courts.sportId],
    isActive = row[Courts.isActive],
    name = row[Courts.name],
    venueId = row[Courts.venueId].value,
    id = row[Courts.id].value,
)

object Courts : IntIdTable() {
    val venueId = reference("venueId", Venues)
    val sportId = varchar("sportId", 255)
    val name = varchar("name", 255)
    val isActive = bool("isActive").default(true)
}
