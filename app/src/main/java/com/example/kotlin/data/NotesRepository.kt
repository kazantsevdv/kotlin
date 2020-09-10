package com.example.kotlin.data

import com.example.kotlin.data.entity.Note
import com.example.kotlin.data.provider.FirestoreDataProvider
import com.example.kotlin.data.provider.RemoteDataProvider


object NotesRepository {
    val remoteProvider: RemoteDataProvider = FirestoreDataProvider()

    fun getNotes() = remoteProvider.subscribeToAllNotes()
    fun saveNote(note: Note) = remoteProvider.saveNote(note)
    fun getNoteById(id: String) = remoteProvider.getNoteById(id)
}