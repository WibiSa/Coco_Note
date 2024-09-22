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
class HomeViewModel @Inject constructor(private val noteRepository: NoteRepository) : ViewModel() {

    private val _listOfNoteUiState = MutableStateFlow<List<Note>>(emptyList())
    val listOfNoteUiState: StateFlow<List<Note>> = _listOfNoteUiState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.getAllNotes().collect { noteList ->
                _listOfNoteUiState.value = noteList
            }
        }
    }

    fun deleteNote(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.deleteNote(id)
        }
    }
}