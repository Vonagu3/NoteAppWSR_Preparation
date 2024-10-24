package com.example.noteappwsr_preparation.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int
)

class InvalidNoteException(message: String): Exception(message)
