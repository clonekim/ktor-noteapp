package note

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object Topics : Table("topics") {
    val id: Column<Int> = integer("id").autoIncrement()
    val subtitle: Column<String> = varchar("subtitle", 100)
    val createAt: Column<LocalDateTime> = datetime("create_at")
    val updateAt: Column<LocalDateTime> = datetime("update_at")
    override val primaryKey = PrimaryKey(id)

}


object Pages : Table("pages") {
    val id: Column<Int> = integer("id").autoIncrement()
    val content: Column<String> = text("content")
    val createAt: Column<LocalDateTime> = datetime("create_at")
    val updateAt: Column<LocalDateTime> = datetime("update_at")
    override val primaryKey = PrimaryKey(id)
}


object Attachments : Table("attachments") {
    val id: Column<Int> = integer("id").autoIncrement()
    val name: Column<String> = text("file_name")
    val size: Column<Long> = long("file_size")
    val contentType: Column<String> = varchar("content_type", 100)
    val refPageId: Column<Int> = integer("page_id")
    val createAt: Column<LocalDateTime> = datetime("create_at")
    override val primaryKey = PrimaryKey(id)
}



data class Topic(
    val id: Int,
    val subtitle: String,
    val createAt: LocalDateTime,
    val updateAt: LocalDateTime
)


data class NewTopic(
    val subtitle: String,
)

data class Page(
    val id: Int,
    val content: String,
    val createAt: LocalDateTime,
    val updateAt: LocalDateTime
)


data class Attachment(
    val id: Int,
    val name: String,
    val size: Long,
    val contentType: String,
    val createAt: LocalDateTime
)