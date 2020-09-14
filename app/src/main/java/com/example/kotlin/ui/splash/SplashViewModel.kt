package com.example.kotlin.ui.splash

import com.example.kotlin.data.NotesRepository
import com.example.kotlin.data.errors.NoAuthException
import com.example.kotlin.ui.base.BaseViewModel


class SplashViewModel : BaseViewModel<Boolean?, SplashViewState>() {

    fun requestUser() {
        NotesRepository.getCurrentUser().observeForever {
            viewStateLiveData.value = it?.let {
                SplashViewState(authenticated = true)
            } ?: SplashViewState(error = NoAuthException())

        }
    }
}
