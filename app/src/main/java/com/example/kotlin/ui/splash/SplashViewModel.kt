package com.example.kotlin.ui.splash


import com.example.kotlin.data.Repository
import com.example.kotlin.data.errors.NoAuthException
import com.example.kotlin.ui.base.BaseViewModel
import kotlinx.coroutines.launch


class SplashViewModel(val notesRepository: Repository) :
    BaseViewModel<Boolean>() {

    fun requestUser() {
        launch {
            notesRepository.getCurrentUser()?.let {
                setData(true)
            } ?: setError(NoAuthException())
        }
    }

}
