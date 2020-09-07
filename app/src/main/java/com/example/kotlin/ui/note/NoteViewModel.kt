package com.example.kotlin.ui.note

import androidx.lifecycle.ViewModel
import com.example.kotlin.data.entity.Note
import com.example.kotlin.data.entity.Repository

class NoteViewModel : ViewModel() {

    private var pendingNote: Note? = null

    fun save(note: Note) {
        pendingNote = note
    }

    override fun onCleared() {
        pendingNote?.let {
            Repository.saveNote(it)
        }
    }

}
