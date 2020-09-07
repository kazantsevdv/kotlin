package com.example.kotlin.data.entity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.*

object Repository {
    private val notesLive = MutableLiveData<List<Note>>()
    val notes = mutableListOf(
        Note(UUID.randomUUID().toString(), "Заметка1", "Текст заметки 1", Note.Color.BLUE),
        Note(UUID.randomUUID().toString(), "Заметка2", "Текст заметки 2", Note.Color.GREEN),
        Note(UUID.randomUUID().toString(), "Заметка3", "Текст заметки 3", Note.Color.VIOLET),
        Note(UUID.randomUUID().toString(), "Заметка4", "Текст заметки 4", Note.Color.RED),
        Note(UUID.randomUUID().toString(), "Заметка5", "Текст заметки 5")
    )

    init {
        notesLive.value = notes
    }

    fun getNotes(): LiveData<List<Note>> {
        return notesLive
    }

    fun saveNote(note: Note) {
        addOrReplace(note)
        notesLive.value = notes
    }

    private fun addOrReplace(note: Note) {
        for (i in notes.indices) {
            if (notes[i] == note) {
                notes[i] = note
                return
            }
        }
        notes.add(note)
    }
}