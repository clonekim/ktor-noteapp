package note

import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import java.time.LocalDateTime


object WikiService {


    suspend fun addTopic(topic: NewTopic): Topic {

        val now = LocalDateTime.now()
        val id = dbQuery {

            Topics.insert {
                it[subtitle] = topic.subtitle
                it[createAt] = now
                it[updateAt] = now
            } get Topics.id

        }

        return Topic(
            id = id,
            subtitle = topic.subtitle,
            createAt = now,
            updateAt = now
        )

    }

    suspend fun getTopics(): List<Topic> = dbQuery {
        Topics.selectAll().map {
            Topic(
                id = it[Topics.id],
                subtitle = it[Topics.subtitle],
                createAt = it[Topics.createAt],
                updateAt = it[Topics.updateAt]
            )
        }
    }


    suspend fun getTopic(id: Int): Topic? = dbQuery {
        Topics.select {
            (Topics.id eq id)
        }.mapNotNull {
            Topic(
                id = it[Topics.id],
                subtitle = it[Topics.subtitle],
                createAt = it[Topics.createAt],
                updateAt = it[Topics.updateAt]
            )
        }.singleOrNull()
    }

    suspend fun updateTopic(topic: Topic): Topic? =
        dbQuery {
            Topics.update({ Topics.id eq topic.id }) {
                it[subtitle] = topic.subtitle
                it[updateAt] = LocalDateTime.now()
            }

            getTopic(topic.id)
        }


}
