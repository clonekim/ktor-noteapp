package note

import io.ktor.server.config.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init(config: ApplicationConfig) {
        Database.connect(
            url = config.property("db.url").getString(),
            driver = config.property("db.driver").getString()
        )

        transaction {
            addLogger(StdOutSqlLogger)
            SchemaUtils.create(Topics, Pages)
        }
    }
}

suspend fun <T> dbQuery(
    block: suspend () -> T
): T = newSuspendedTransaction { block() }
