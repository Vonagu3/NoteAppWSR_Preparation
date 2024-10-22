package com.example.noteappwsr_preparation.domain.repository


import android.database.sqlite.SQLiteException
import com.example.noteappwsr_preparation.data.model.Note
import kotlinx.coroutines.flow.Flow
import kotlin.jvm.Throws

interface NoteRepository {

    fun getNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note?

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)
}