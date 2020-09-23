package com.example.kotlin.data.provider

import com.example.kotlin.data.entity.Note
import com.example.kotlin.data.entity.User
import com.example.kotlin.data.model.Result
import kotlinx.coroutines.channels.ReceiveChannel


interface RemoteDataProvider {
    fun subscribeToAllNotes(): ReceiveChannel<Result>
    suspend fun getNoteById(id: String): Note
    suspend fun saveNote(note: Note): Note
    suspend fun getCurrentUser(): User?
    suspend fun deleteNote(noteId: String)
}