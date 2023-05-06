package com.nestorsgarzonc.features.owner.model

import com.nestorsgarzonc.features.venue.model.Venues
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ResultRow

@Serializable
data class User(
    val username: String,
    val birthday: String,
    val email: String,
    val id: Int,
    val isActive: Boolean,
)

@Serializable
data class OwnerPopulated(val id: Int, val venueId: Int, val user: User)

@Serializable
data class Owner(val id: Int, val venueId: Int, val userId: String)

@Serializable
data class AddOwner(val venueId: Int, val userId: String)

fun resultRowToOwner(row: ResultRow) = Owner(
    id = row[Owners.id].value,
    venueId = row[Owners.venueId].value,
    userId = row[Owners.userId]
)

object Owners : IntIdTable() {
    val venueId = reference("venueId", Venues)
    val userId = varchar("userId", length = 255)
}
