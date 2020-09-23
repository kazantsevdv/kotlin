package com.example.kotlin.ui.note


import androidx.annotation.VisibleForTesting
import com.example.kotlin.data.Repository
import com.example.kotlin.data.entity.Note
import com.example.kotlin.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class NoteViewModel(val repository: Repository) : BaseViewModel<NoteViewState.Data>() {


    private val pendingNote: Note?
        get() = getViewState().poll()?.note


    fun save(note: Note) {
        setData(NoteViewState.Data(note = note))

    }


    fun loadNote(noteId: String) {
        launch {
            try {
                repository.getNoteById(noteId).let {
                    setData(NoteViewState.Data(note = it))
                }
            } catch (e: Throwable) {
                setError(e)
            }
        }
    }



    //    fun saveChanges(note: Note) {
//        viewStateLiveData.value = NoteViewState(NoteViewState.Data(note = note))
//    }
    @VisibleForTesting
    public override fun onCleared() {
        launch {
            pendingNote?.let { repository.saveNote(it) }
        }
    }


    fun deleteNote() {
        launch {
            try {
                pendingNote?.let { repository.deleteNote(it.id) }
                setData(NoteViewState.Data(isDeleted = true))
            } catch (e: Throwable) {
                setError(e)
            }
        }
    }

}
