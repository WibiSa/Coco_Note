package me.wibisa.coconote.core.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.wibisa.coconote.core.data.local.database.dao.NoteDao
import me.wibisa.coconote.core.data.local.database.entities.toDomain
import me.wibisa.coconote.core.domain.model.Note
import me.wibisa.coconote.core.domain.model.toEntity
import me.wibisa.coconote.core.domain.repository.NoteRepository
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(private val noteDao: NoteDao) : NoteRepository {
    override suspend fun insertNote(note: Note) = noteDao.insertNote(note.toEntity())

    override suspend fun updateNote(note: Note) = noteDao.updateNote(note.toEntity())

    override fun getAllNotes(): Flow<List<Note>> =
        noteDao.getAllNotes().map { notes -> notes.map { it.toDomain() } }

    override suspend fun deleteNote(id: String) = noteDao.deleteNote(id)
}