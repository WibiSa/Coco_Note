package me.wibisa.coconote.core.domain.repository

import kotlinx.coroutines.flow.Flow
import me.wibisa.coconote.core.domain.model.Note

interface NoteRepository {
    suspend fun insertNote(note: Note)
    suspend fun updateNote(note: Note)
    fun getAllNotes(): Flow<List<Note>>
    suspend fun deleteNote(id: String)
}