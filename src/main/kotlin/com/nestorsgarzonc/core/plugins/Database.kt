package com.nestorsgarzonc.core.plugins
import com.nestorsgarzonc.features.court.model.Courts
import com.nestorsgarzonc.features.owner.model.Owners
import com.nestorsgarzonc.features.photos.model.Photos
import com.nestorsgarzonc.features.schedule.model.Schedules
import com.nestorsgarzonc.features.venue.model.Venues
import io.ktor.server.config.*
import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*
import org.jetbrains.exposed.sql.transactions.experimental.*

object DatabaseFactory {
    fun init(config: ApplicationConfig) {
        val driverClassName = "com.impossibl.postgres.jdbc.PGDriver"
        val url = config.propertyOrNull("deployment.db.url")?.getString() ?: "localhost"
        val port = config.propertyOrNull("deployment.db.port")?.getString() ?: "5432"
        val db = config.propertyOrNull("deployment.db.dbName")?.getString() ?: "tourment_venue"
        val jdbcURL = "jdbc:pgsql://$url:$port/$db"
        val database = Database.connect(
            jdbcURL,
            driverClassName,
            config.propertyOrNull("deployment.db.username")?.getString() ?: "segc_db",
            config.propertyOrNull("deployment.db.password")?.getString() ?: "123"

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