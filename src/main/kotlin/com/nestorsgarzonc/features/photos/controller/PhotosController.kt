package com.nestorsgarzonc.features.photos.controller

import com.nestorsgarzonc.core.failure.Failure
import com.nestorsgarzonc.core.plugins.DatabaseFactory.dbQuery
import com.nestorsgarzonc.features.photos.model.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class PhotosController {
    suspend fun getAllPhotos(): List<Photo> = dbQuery {
        Photos.selectAll().map(::resultRowToPhoto)
    }

    suspend fun getPhotoById(id: Int): Photo? = dbQuery {
        Photos
            .select { Photos.id eq id }
            .map(::resultRowToPhoto)
            .singleOrNull()
    }

    suspend fun getPhotosByCourtId(id: Int): Photo? = dbQuery {
        Photos
            .select { Photos.courtId eq id }
            .map(::resultRowToPhoto)
            .singleOrNull()
    }

    suspend fun createPhoto(court: AddPhoto): Failure? = dbQuery {
        val insertStatement = Photos.insert {
            it[courtId] = court.courtId
            it[url] = court.url
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToPhoto)
            ?: return@dbQuery Failure("No se pudo crear la foto")
        return@dbQuery null
    }

    suspend fun updatePhoto(id: Int, photo: UpdatePhoto): Failure? = dbQuery {
        val insertStatement = Photos.update({ Photos.id eq id }) {
            if (photo.courtId != null) it[courtId] = photo.courtId
            if (photo.url != null) it[url] = photo.url
        }
        if (insertStatement > 0) {
            return@dbQuery null
        }
        return@dbQuery Failure("Ha ocurrido un error actualizando la foto")
    }

    suspend fun deletePhotoById(id: Int): Failure? = dbQuery {
        val res = Photos.deleteWhere { Photos.id eq id }
        if (res > 0) {
            return@dbQuery null
        }
        return@dbQuery Failure("Ha ocurrido un error eliminando la foto")
    }
}
