package com.example.kotlin.data.provider

import androidx.lifecycle.LiveData
import com.example.kotlin.data.entity.Note
import com.example.kotlin.data.entity.User
import com.example.kotlin.data.model.Result


interface RemoteDataProvider {
    fun subscribeToAllNotes(): LiveData<Result>
    fun getNoteById(id: String): LiveData<Result>
    fun saveNote(note: Note): LiveData<Result>
    fun getCurrentUser(): LiveData<User?>
    fun deleteNote(noteId: String): LiveData<Result>
}