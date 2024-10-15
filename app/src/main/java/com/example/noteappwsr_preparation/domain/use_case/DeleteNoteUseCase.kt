package com.example.noteappwsr_preparation.domain.use_case

import com.example.noteappwsr_preparation.data.model.Note
import com.example.noteappwsr_preparation.domain.repository.NoteRepository

class DeleteNoteUseCase(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }
}