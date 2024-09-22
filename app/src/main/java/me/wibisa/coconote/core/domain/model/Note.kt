package me.wibisa.coconote.core.domain.model

import me.wibisa.coconote.core.data.local.database.entities.NoteEntity

data class Note(
    val id: String,
    val title: String,
    val description: String,
    val date: String,
    val timestamp: String
)

fun Note.toEntity(): NoteEntity {
    return NoteEntity(
        id = id,
        title = title,
        description = description,
        date = date,
        timestamp = timestamp
    )
}
