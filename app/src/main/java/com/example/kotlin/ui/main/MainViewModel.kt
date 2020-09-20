package com.example.kotlin.ui.main


import androidx.annotation.VisibleForTesting
import androidx.lifecycle.Observer
import com.example.kotlin.data.Repository
import com.example.kotlin.data.entity.Note
import com.example.kotlin.data.model.Result
import com.example.kotlin.data.model.Result.Error
import com.example.kotlin.data.model.Result.Success
import com.example.kotlin.ui.base.BaseViewModel


class MainViewModel(val notesRepository: Repository) : BaseViewModel<List<Note>?, MainViewState>() {

    private val notesObserver = Observer<Result> { result ->
        result ?: return@Observer
        when (result) {
            is Success<*> -> {
                @Suppress("UNCHECKED_CAST")
                viewStateLiveData.value = MainViewState(notes = result.data as? List<Note>)
            }
            is Error -> {
                viewStateLiveData.value = MainViewState(error = result.error)
            }
        }
    }

    private val repositoryNotes = notesRepository.getNotes()

    init {
        viewStateLiveData.value = MainViewState()
        repositoryNotes.observeForever(notesObserver)
    }

    @VisibleForTesting
    public override fun onCleared() {
        repositoryNotes.removeObserver(notesObserver)
        super.onCleared()
    }
}
