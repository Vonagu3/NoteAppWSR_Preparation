package com.example.noteappwsr_preparation.domain.use_case

import com.example.noteappwsr_preparation.data.model.InvalidNoteException
import com.example.noteappwsr_preparation.data.model.Note
import com.example.noteappwsr_preparation.domain.repository.NoteRepository

class AddNoteUseCase(
    private val repository: NoteRepository
) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("Заголовок заметки не заполнен")
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException("Контент заметки не заполнен")
        }
        repository.insertNote(note)
    }
}