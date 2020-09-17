package com.example.kotlin.data

import com.example.kotlin.data.entity.Note

import com.example.kotlin.data.provider.RemoteDataProvider


class Repository(private val remoteProvider: RemoteDataProvider) {


    fun getNotes() = remoteProvider.subscribeToAllNotes()
    fun saveNote(note: Note) = remoteProvider.saveNote(note)
    fun getNoteById(id: String) = remoteProvider.getNoteById(id)
    fun getCurrentUser() = remoteProvider.getCurrentUser()
    fun deleteNote(noteId: String) = remoteProvider.deleteNote(noteId)
}