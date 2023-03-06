package com.nestorsgarzonc.core.plugins
import com.nestorsgarzonc.features.court.model.Courts
import com.nestorsgarzonc.features.owner.model.Owners
import com.nestorsgarzonc.features.photos.model.Photos
import com.nestorsgarzonc.features.schedule.model.Schedules
import com.nestorsgarzonc.features.venue.model.Venues
import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*
import org.jetbrains.exposed.sql.transactions.experimental.*

object DatabaseFactory {
    fun init() {
        val driverClassName = "com.impossibl.postgres.jdbc.PGDriver"
        val jdbcURL = "jdbc:pgsql://localhost:5432/tourment_venue"
        val database = Database.connect(
            jdbcURL,
            driverClassName,
            "segc_db",
            "123"
        )
        transaction(database) {
            SchemaUtils.create(Courts)
            SchemaUtils.create(Owners)
            SchemaUtils.create(Photos)
            SchemaUtils.create(Schedules)
            SchemaUtils.create(Venues)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}