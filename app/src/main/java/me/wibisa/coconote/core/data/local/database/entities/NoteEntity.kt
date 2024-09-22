package me.wibisa.coconote.core.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import me.wibisa.coconote.core.domain.model.Note

@Entity
data class NoteEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val date: String,
    val timestamp: String
)

fun NoteEntity.toDomain(): Note {
    return Note(
        id = id,
        title = title,
        description = description,
        date = date,
        timestamp = timestamp
    )
}