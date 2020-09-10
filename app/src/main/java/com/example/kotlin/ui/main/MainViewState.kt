package com.example.kotlin.ui.main

import com.example.kotlin.data.entity.Note
import com.example.kotlin.ui.base.BaseViewState


class MainViewState(val notes: List<Note>? = null, error: Throwable? = null) :
    BaseViewState<List<Note>?>(notes, error)