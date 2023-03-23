package com.nestorsgarzonc.features.venue.controller

import com.nestorsgarzonc.core.failure.Failure
import com.nestorsgarzonc.core.plugins.DatabaseFactory.dbQuery
import com.nestorsgarzonc.features.owner.model.Owners
import com.nestorsgarzonc.features.owner.model.resultRowToOwner
import com.nestorsgarzonc.features.venue.model.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class VenueController {
    suspend fun getAllVenues(): List<Venue> = dbQuery {
        try{
            Venues.selectAll().map(::resultRowToVenue)
        }catch (e: ExposedSQLException) {
            return@dbQuery emptyList()
        }
    }

    suspend fun getVenueById(id: Int): Venue? = dbQuery {
        try{
            Venues
                .select { Venues.id eq id }
                .map(::resultRowToVenue)
                .singleOrNull()
        }catch (e: ExposedSQLException) {
            return@dbQuery null
        }
    }

    suspend fun getVenueByUserId(id: String): Venue? = dbQuery {
        try{
            val user = Owners
                .select { Owners.userId eq id }
                .map(::resultRowToOwner)
                .singleOrNull() ?: return@dbQuery null
            Venues
                .select { Venues.id eq user.venueId }
                .map(::resultRowToVenue)
                .singleOrNull()
        }catch (e: ExposedSQLException) {
            return@dbQuery null
        }
    }

    suspend fun createVenue(owner: AddVenue): Failure? = dbQuery {
        try{
            val insertStatement = Venues.insert {
                it[location] = owner.location
                it[description] = owner.description
                it[isActive] = owner.isActive
            }
            insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToVenue)
                ?: return@dbQuery Failure("No se pudo crear la sede")
            return@dbQuery null
        }catch (e: ExposedSQLException) {
            return@dbQuery Failure("No se pudo crear la sede")
        }
    }

    suspend fun updateVenue(id: Int, owner: UpdateVenue): Failure? = dbQuery {
        try{
            val insertStatement = Venues.update({ Venues.id eq id }) {
                if (owner.location != null) it[location] = owner.location
                if (owner.description != null) it[description] = owner.description
                if (owner.isActive != null) it[isActive] = owner.isActive
            }
            if (insertStatement > 0) {
                return@dbQuery null
            }
            return@dbQuery Failure("Ha ocurrido un error actualizando la sede")
        }catch (e: ExposedSQLException) {
            return@dbQuery Failure("Ha ocurrido un error actualizando la sede")
        }
    }

    suspend fun deleteVenueById(id: Int): Failure? = dbQuery {
        try{
            val res = Venues.deleteWhere { Venues.id eq id }
            if (res > 0) {
                return@dbQuery null
            }
            return@dbQuery Failure("Ha ocurrido un error eliminando la sede")
        }catch (e: ExposedSQLException) {
            return@dbQuery Failure("Ha ocurrido un error eliminando la sede")
        }
    }
}
