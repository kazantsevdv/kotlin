package com.example.kotlin.ui.main


import com.example.kotlin.data.Repository
import com.example.kotlin.data.entity.Note
import com.example.kotlin.data.model.Result.Error
import com.example.kotlin.data.model.Result.Success
import com.example.kotlin.ui.base.BaseViewModel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch


class MainViewModel(val notesRepository: Repository) : BaseViewModel<List<Note>?>() {
    private val notesChannel = notesRepository.getNotes()

    init {
        launch {
            notesChannel.consumeEach {
                when (it) {
                    is Success<*> -> setData(it.data as? List<Note>)
                    is Error -> setError(it.error)
                }
            }
        }
    }

    override fun onCleared() {
        notesChannel.cancel()
        super.onCleared()
    }

}
