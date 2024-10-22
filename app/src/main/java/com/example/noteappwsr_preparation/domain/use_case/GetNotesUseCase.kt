package com.example.noteappwsr_preparation.domain.use_case

import com.example.noteappwsr_preparation.data.model.Note
import com.example.noteappwsr_preparation.domain.repository.NoteRepository
import com.example.noteappwsr_preparation.domain.util.NoteOrder
import com.example.noteappwsr_preparation.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.reflect.KFunction1

class GetNotesUseCase(
    private val repository: NoteRepository
) {
    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ): Flow<List<Note>> {
        return repository.getNotes().map { notes ->
            when (noteOrder.orderType) {
                is OrderType.Ascending -> sorting(noteOrder, notes::sortedBy)
                is OrderType.Descending -> sorting(noteOrder, notes::sortedByDescending)
            }
        }
    }

    private fun sorting(noteOrder: NoteOrder, func: KFunction1<(Note) -> String, List<Note>>): List<Note> =
        when (noteOrder) {
            is NoteOrder.Title -> func.invoke { it.title.lowercase() }
            is NoteOrder.Date -> func.invoke { it.timestamp.toString() }
            is NoteOrder.Color -> func { it.color.toString() }
        }
}