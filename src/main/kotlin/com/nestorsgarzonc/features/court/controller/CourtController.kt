package com.nestorsgarzonc.features.court.controller

import com.nestorsgarzonc.core.failure.Failure
import com.nestorsgarzonc.core.plugins.DatabaseFactory.dbQuery
import com.nestorsgarzonc.features.court.model.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class CourtController {
    suspend fun getAllCourts(): List<Court> = dbQuery {
        try {
            Courts.selectAll().map(::resultRowToCourt)
        } catch (e: ExposedSQLException) {
            return@dbQuery emptyList<Court>()
        }
    }

    suspend fun getCourtById(id: Int): Court? = dbQuery {
        try {
            Courts
                .select { Courts.id eq id }
                .map(::resultRowToCourt)
                .singleOrNull()
        } catch (e: ExposedSQLException) {
            return@dbQuery null
        }
    }

    suspend fun getCourtsByVenueId(id: Int): Court? = dbQuery {
        try {
            Courts
                .select { Courts.venueId eq id }
                .map(::resultRowToCourt)
                .singleOrNull()
        } catch (e: ExposedSQLException) {
            return@dbQuery null
        }
    }

    suspend fun getCourtsBySportId(id: String): Court? = dbQuery {
        try {
            Courts
                .select { Courts.sportId eq id }
                .map(::resultRowToCourt)
                .singleOrNull()
        } catch (e: ExposedSQLException) {
            return@dbQuery null
        }
    }

    suspend fun createCourt(court: AddCourt): Failure? = dbQuery {
        try {
            val insertStatement = Courts.insert {
                it[venueId] = court.venueId
                it[sportId] = court.sportId
                it[isActive] = court.isActive
            }
            insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToCourt)
                ?: return@dbQuery Failure("No se pudo crear la cancha")
            return@dbQuery null
        } catch (e: ExposedSQLException) {
            return@dbQuery Failure("No se pudo crear la cancha")
        }
    }

    suspend fun updateCourt(id: Int, court: UpdateCourt): Failure? = dbQuery {
        try {
            val insertStatement = Courts.update({ Courts.id eq id }) {
                if (court.venueId != null) it[venueId] = court.venueId
                if (court.sportId != null) it[sportId] = court.sportId
                if (court.isActive != null) it[isActive] = court.isActive
            }
            if (insertStatement > 0) {
                return@dbQuery null
            }
            return@dbQuery Failure("Ha ocurrido un error actualizando la cancha")
        } catch (e: ExposedSQLException) {
            return@dbQuery Failure("Ha ocurrido un error actualizando la cancha")
        }
    }

    suspend fun deleteCourtById(id: Int): Failure? = dbQuery {
        try {
            val res = Courts.deleteWhere { Courts.id eq id }
            if (res > 0) {
                return@dbQuery null
            }
            return@dbQuery Failure("Ha ocurrido un error eliminando la cancha")
        } catch (e: ExposedSQLException) {
            return@dbQuery Failure("Ha ocurrido un error eliminando la cancha")
        }
    }
}
