package com.example.kotlin.ui.main


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlin.data.entity.Repository


class MainViewModel : ViewModel() {

    private val viewStateLiveData: MutableLiveData<MainViewState> = MutableLiveData()

    init {
        Repository.getNotes().observeForever { notes ->
            notes?.let {
                viewStateLiveData.value =
                    viewStateLiveData.value?.copy(notes = notes) ?: MainViewState(notes)
            }

        }
    }

    fun viewState(): LiveData<MainViewState> = viewStateLiveData
}