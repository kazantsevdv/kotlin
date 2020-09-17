package com.example.kotlin.ui.note


import com.example.kotlin.data.Repository
import com.example.kotlin.data.entity.Note
import com.example.kotlin.data.model.Result
import com.example.kotlin.ui.base.BaseViewModel

class NoteViewModel(val repository: Repository) : BaseViewModel<NoteViewState.Data,
        NoteViewState>() {

    private val pendingNote: Note?
        get() = viewStateLiveData.value?.data?.note


    fun save(note: Note) {
        viewStateLiveData.value = NoteViewState(NoteViewState.Data(note = note))
    }


    fun loadNote(noteId: String) {
        repository.getNoteById(noteId).observeForever { t ->
            t?.let {
                viewStateLiveData.value = when (t) {
                    is Result.Success<*> -> NoteViewState(NoteViewState.Data(note = t.data as? Note))

                    is Result.Error -> NoteViewState(error = t.error)
                }
            }
        }
    }


    fun saveChanges(note: Note) {
        viewStateLiveData.value = NoteViewState(NoteViewState.Data(note = note))
    }

    override fun onCleared() {
        pendingNote?.let { repository.saveNote(it) }
    }


    fun deleteNote() {
        pendingNote?.let {
            repository.deleteNote(it.id).observeForever { t ->
                t?.let {
                    viewStateLiveData.value = when (it) {
                        is Result.Success<*> -> NoteViewState(NoteViewState.Data(isDeleted = true))
                        is Result.Error -> NoteViewState(error = it.error)
                    }
                }
            }
        }
    }


}
