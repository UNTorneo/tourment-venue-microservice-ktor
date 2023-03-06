package com.nestorsgarzonc.features.owner.controller

import com.nestorsgarzonc.core.failure.Failure
import com.nestorsgarzonc.features.owner.model.*
import com.nestorsgarzonc.core.plugins.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*

class OwnerController {
    suspend fun getAllOwners(): List<Owner> = dbQuery {
        Owners.selectAll().map(::resultRowToOwner)
    }

    suspend fun getOwnerById(id: Int): Owner? = dbQuery {
        Owners
            .select { Owners.id eq id }
            .map(::resultRowToOwner)
            .singleOrNull()
    }

    suspend fun getOwnerByVenueId(id: Int): Owner? = dbQuery {
        Owners
            .select { Owners.venueId eq id }
            .map(::resultRowToOwner)
            .singleOrNull()
    }

    suspend fun deleteOwnerById(id: Int): Owner? = dbQuery {
        Owners
            .select { Owners.id eq id }
            .map(::resultRowToOwner)
            .singleOrNull()
    }

    suspend fun createOwner(owner: AddOwner): Failure? = dbQuery {
        val insertStatement = Owners.insert {
            it[userId] = owner.userId
            it[venueId] = owner.venueId
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToOwner)
            ?: return@dbQuery Failure("No se pudo crear el dueño")
        return@dbQuery null
    }
}
