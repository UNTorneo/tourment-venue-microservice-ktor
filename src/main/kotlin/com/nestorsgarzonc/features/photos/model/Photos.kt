package com.nestorsgarzonc.features.photos.model

import com.nestorsgarzonc.features.court.model.Courts
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ResultRow

@Serializable
data class Photo(val id: Int, val courtId: Int, val url: String)

@Serializable
data class AddPhoto(
    val courtId: Int,
    val url: String,
)

@Serializable
data class UpdatePhoto(
    val id: Int? = null,
    val courtId: Int? = null,
    val url: String? = null,
)

fun resultRowToPhoto(row: ResultRow) = Photo(
    id = row[Photos.id].value,
    courtId = row[Photos.courtId].value,
    url = row[Photos.url],
)

object Photos : IntIdTable() {
    val courtId = reference("courtId", Courts)
    val url = varchar("url", 255)
}
