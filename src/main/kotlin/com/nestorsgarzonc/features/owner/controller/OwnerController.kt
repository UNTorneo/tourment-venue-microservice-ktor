package com.nestorsgarzonc.features.owner.controller

import com.nestorsgarzonc.core.failure.Failure
import com.nestorsgarzonc.features.owner.model.*
import com.nestorsgarzonc.core.plugins.DatabaseFactory.dbQuery
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class OwnerController {
    suspend fun getAllOwners(): List<Owner> = dbQuery {
        try {
            Owners.selectAll().map(::resultRowToOwner)
        } catch (e: ExposedSQLException) {
            return@dbQuery emptyList<Owner>()
        }
    }

    suspend fun getOwnerById(id: Int): Owner? = dbQuery {
        try {
            Owners
                .select { Owners.id eq id }
                .map(::resultRowToOwner)
                .singleOrNull()
        } catch (e: ExposedSQLException) {
            return@dbQuery null
        }
    }

    suspend fun getOwnerByVenueId(id: Int): Owner? = dbQuery {
        try {
            Owners
                .select { Owners.venueId eq id }
                .map(::resultRowToOwner)
                .singleOrNull()
        } catch (e: ExposedSQLException) {
            return@dbQuery null
        }
    }

    suspend fun deleteOwnerById(id: Int): Failure? = dbQuery {
        try {
            val res = Owners.deleteWhere { Owners.id eq id }
            if (res > 0) {
                return@dbQuery null
            }
            return@dbQuery Failure("Ha ocurrido un error eliminando el dueño")
        } catch (e: ExposedSQLException) {
            return@dbQuery Failure("Ha ocurrido un error eliminando el dueño")
        }
    }

    suspend fun createOwner(owner: AddOwner): Failure? = dbQuery {
        try {
            val insertStatement = Owners.insert {
                it[userId] = owner.userId
                it[venueId] = owner.venueId
            }
            insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToOwner)
                ?: return@dbQuery Failure("No se pudo crear el dueño")
            return@dbQuery null
        } catch (e: ExposedSQLException) {
            return@dbQuery Failure("No se pudo crear el dueño")
        }
    }
}
