package com.example.noteappwsr_preparation.presentation.add_edit_note

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun AddEditNoteScreen(
    navController: NavController,
    noteColor: Int,
//    viewModel
) {
    Text("Hello World!!! $noteColor")
}