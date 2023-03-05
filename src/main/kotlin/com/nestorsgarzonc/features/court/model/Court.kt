package com.nestorsgarzonc.features.court.model

import com.nestorsgarzonc.features.schedule.model.Schedules
import com.nestorsgarzonc.features.venue.model.Venues
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable

@Serializable
data class Court(val id: Int, val venueId: Int, val sportId: String, val scheduleId: Int)

object Courts: IntIdTable() {
    val venueId = reference("venueId", Venues)
    val scheduleId = reference("scheduleId", Schedules)
    val sportId = varchar("sportId", 255)
    val isActive= bool("isActive").default(true)
}
