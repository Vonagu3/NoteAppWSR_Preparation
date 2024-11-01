package com.example.noteappwsr_preparation.presentation.add_edit_note

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.noteappwsr_preparation.AddEditNoteScreen
import com.example.noteappwsr_preparation.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.toRoute<AddEditNoteScreen>().let {
            if (it.noteId !== null && it.noteId != -1) {
                viewModelScope.launch {
                    try {
                        noteUseCases.getNote(it.noteId)?.let { note ->

                        }
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch (e: Throwable) {
                        _eventFlow.emit(UiEvent.ShowSnackbar(e.message.toString()))
                    }

                }
            }
        }
    }

}

sealed class UiEvent {
    data class ShowSnackbar(val message: String): UiEvent()
    object SaveNote: UiEvent()
}