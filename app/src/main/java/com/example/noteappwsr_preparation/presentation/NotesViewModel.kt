package com.example.noteappwsr_preparation.presentation

import android.database.sqlite.SQLiteException
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteappwsr_preparation.data.model.Note
import com.example.noteappwsr_preparation.domain.use_case.NoteUseCases
import com.example.noteappwsr_preparation.domain.util.NoteOrder
import com.example.noteappwsr_preparation.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

// MVVM - Model - View - ViewModel
// Jetpack compose
@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() { // TODO: ДЗ Создать абстрактную BaseViewModel с предопределённым handleError и по возможности вынести все общие механизмы туда

    private val _state = mutableStateOf(NoteState())
    val state: State<NoteState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var getNotesJob: Job? = null

    private var recentlyDeletedNote: Note? = null

    init {
        getNotes()
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.DeleteNote -> {
                handleError {
                    recentlyDeletedNote = event.note
                    noteUseCases.deleteNote(event.note)
                }
            }

            is NotesEvent.Order -> {
                getNotes(event.noteOrder)
            }

            NotesEvent.RestoreNote -> {
                handleError {
                    noteUseCases.addNote(recentlyDeletedNote ?: return@handleError)
                    recentlyDeletedNote = null
                }
            }

            NotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun handleError(block: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                block()
            } catch (e: Exception) {
                _eventFlow.emit(UiEvent.ShowSnackbar(e.message.toString()))
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotes().onEach { notes ->
            _state.value = state.value.copy(
                notes = notes,
                noteOrder = noteOrder
            )
        }.launchIn(viewModelScope)
    }
}