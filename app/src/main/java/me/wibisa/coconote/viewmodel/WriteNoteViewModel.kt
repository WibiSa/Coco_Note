package me.wibisa.coconote.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.wibisa.coconote.core.domain.model.Note
import me.wibisa.coconote.core.domain.repository.NoteRepository
import javax.inject.Inject

@HiltViewModel
class WriteNoteViewModel @Inject constructor(private val noteRepository: NoteRepository) :
    ViewModel() {

    private val _listOfNoteUiState = MutableStateFlow<List<Note>>(emptyList())
    val listOfNoteUiState: StateFlow<List<Note>> = _listOfNoteUiState

    fun createNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.insertNote(note)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.updateNote(note)
        }
    }
}