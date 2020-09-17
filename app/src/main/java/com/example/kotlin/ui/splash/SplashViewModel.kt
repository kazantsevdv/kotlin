package com.example.kotlin.ui.splash


import com.example.kotlin.data.Repository
import com.example.kotlin.data.errors.NoAuthException
import com.example.kotlin.ui.base.BaseViewModel


class SplashViewModel(val notesRepository: Repository) :
    BaseViewModel<Boolean?, SplashViewState>() {

    fun requestUser() {
        notesRepository.getCurrentUser().observeForever {
            viewStateLiveData.value = it?.let {
                SplashViewState(authenticated = true)
            } ?: SplashViewState(error = NoAuthException())

        }
    }
}
