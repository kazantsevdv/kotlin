package com.example.kotlin.ui.splash

import com.example.kotlin.ui.base.BaseViewState


class SplashViewState(authenticated: Boolean? = null, error: Throwable? = null) :
    BaseViewState<Boolean?>(authenticated, error)