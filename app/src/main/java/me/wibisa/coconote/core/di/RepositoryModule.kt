package me.wibisa.coconote.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.wibisa.coconote.core.data.repository.NoteRepositoryImpl
import me.wibisa.coconote.core.domain.repository.NoteRepository


@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindNoteRepository(impl: NoteRepositoryImpl): NoteRepository
}