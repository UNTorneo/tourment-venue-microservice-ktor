package com.nestorsgarzonc.features.photos.model
import com.nestorsgarzonc.features.court.model.Courts
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable

@Serializable
data class Photo(val id: Int, val courtId: Int, val url: String)

object Photos: IntIdTable() {
    val courtId = reference("courtId", Courts)
    val url= varchar("url", 255)
}
