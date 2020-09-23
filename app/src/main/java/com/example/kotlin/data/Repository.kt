package com.example.kotlin.data

import com.example.kotlin.data.entity.Note
import com.example.kotlin.data.provider.RemoteDataProvider


class Repository(private val remoteProvider: RemoteDataProvider) {
    fun getNotes() = remoteProvider.subscribeToAllNotes()
    suspend fun saveNote(note: Note) =
        remoteProvider.saveNote(note)


    suspend fun getNoteById(id: String) =
        remoteProvider.getNoteById(id)

    suspend fun getCurrentUser() =
        remoteProvider.getCurrentUser()


    suspend fun deleteNote(noteId: String) =
        remoteProvider.deleteNote(noteId)

}