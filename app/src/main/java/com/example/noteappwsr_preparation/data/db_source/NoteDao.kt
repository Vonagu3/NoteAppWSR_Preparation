package com.example.noteappwsr_preparation.data.db_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.noteappwsr_preparation.data.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    // ДЗ: Room Query посмотреть варианты генерации запросов CRUD -> Create, Read, Update, Delete
    @Query("SELECT * FROM note")
    fun getNotes(): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteById(id: Int): Note?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)
}