package com.nestorsgarzonc.features.owner.model

import com.nestorsgarzonc.features.venue.model.Venues
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable

@Serializable
data class Owner(val id: Int, val venueId: Int, val userId: String)

object Owners:  IntIdTable() {
    val venueId = reference("venueId", Venues)
    val userId = varchar("userId", length = 255)
}
