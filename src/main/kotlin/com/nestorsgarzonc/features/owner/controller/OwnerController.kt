package com.nestorsgarzonc.features.owner.controller

import com.nestorsgarzonc.core.env.EnvManager
import com.nestorsgarzonc.core.failure.Failure
import com.nestorsgarzonc.features.owner.model.*
import com.nestorsgarzonc.core.plugins.DatabaseFactory.dbQuery
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.apache.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class OwnerController {
    private val httpClient = HttpClient(Apache) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            }
            )
        }
    }

    suspend fun getAllOwners(): List<Owner> = dbQuery {
        try {
            Owners.selectAll().map(::resultRowToOwner)
        } catch (e: ExposedSQLException) {
            return@dbQuery emptyList<Owner>()
        }
    }

    suspend fun getOwnerById(id: Int): OwnerPopulated? = dbQuery {
        try {
            val owner = Owners
                .select { Owners.id eq id }
                .map(::resultRowToOwner)
                .singleOrNull()
            owner?.let {
                val userMsReq = httpClient.get("${EnvManager.usersMS}/users/${it.userId}")
                if (userMsReq.status != HttpStatusCode.OK) {
                    return@dbQuery null
                }
                val userMS: User = userMsReq.body()
                OwnerPopulated(
                    it.id,
                    owner.venueId,
                    userMS
                )
            }
        } catch (e: ExposedSQLException) {
            return@dbQuery null
        }
    }

    suspend fun getOwnerByVenueId(id: Int): OwnerPopulated? = dbQuery {
        try {
            val owner = Owners
                .select { Owners.venueId eq id }
                .map(::resultRowToOwner)
                .singleOrNull()
            owner?.let {
                val userMsReq = httpClient.get("${EnvManager.usersMS}/users/${it.userId}")
                if (userMsReq.status != HttpStatusCode.OK) {
                    return@dbQuery null
                }
                val userMS: User = userMsReq.body()
                OwnerPopulated(
                    it.id,
                    owner.venueId,
                    userMS
                )
            }
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
            val userMsReq = httpClient.get("${EnvManager.usersMS}/users/${owner.userId}")
            if (userMsReq.status != HttpStatusCode.OK) {
                return@dbQuery Failure("El usuario no existe")
            }
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
