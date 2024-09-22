package me.wibisa.coconote.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import me.wibisa.coconote.core.data.local.database.dao.NoteDao
import me.wibisa.coconote.core.data.local.database.entities.NoteEntity

@Database(entities = [NoteEntity::class], version = 3, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}