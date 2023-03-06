package com.nestorsgarzonc.features.court.controller

import com.nestorsgarzonc.core.failure.Failure
import com.nestorsgarzonc.core.plugins.DatabaseFactory.dbQuery
import com.nestorsgarzonc.features.court.model.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class CourtController {
    suspend fun getAllCourts(): List<Court> = dbQuery {
        Courts.selectAll().map(::resultRowToCourt)
    }

    suspend fun getCourtById(id: Int): Court? = dbQuery {
        Courts
            .select { Courts.id eq id }
            .map(::resultRowToCourt)
            .singleOrNull()
    }

    suspend fun getCourtsByVenueId(id: Int): Court? = dbQuery {
        Courts
            .select { Courts.venueId eq id }
            .map(::resultRowToCourt)
            .singleOrNull()
    }

    suspend fun getCourtsBySportId(id: String): Court? = dbQuery {
        Courts
            .select { Courts.sportId eq id }
            .map(::resultRowToCourt)
            .singleOrNull()
    }

    suspend fun createCourt(court: AddCourt): Failure? = dbQuery {
        val insertStatement = Courts.insert {
            it[venueId] = court.venueId
            it[sportId] = court.sportId
            it[isActive] = court.isActive
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToCourt)
            ?: return@dbQuery Failure("No se pudo crear la cancha")
        return@dbQuery null
    }

    suspend fun updateCourt(id: Int, court: UpdateCourt): Failure? = dbQuery {
        val insertStatement = Courts.update({ Courts.id eq id }) {
            if (court.venueId != null) it[venueId] = court.venueId
            if (court.sportId != null) it[sportId] = court.sportId
            if (court.isActive != null) it[isActive] = court.isActive
        }
        if (insertStatement > 0) {
            return@dbQuery null
        }
        return@dbQuery Failure("Ha ocurrido un error actualizando la cancha")
    }

    suspend fun deleteCourtById(id: Int): Failure? = dbQuery {
        val res = Courts.deleteWhere { Courts.id eq id }
        if (res > 0) {
            return@dbQuery null
        }
        return@dbQuery Failure("Ha ocurrido un error eliminando la cancha")
    }
}
