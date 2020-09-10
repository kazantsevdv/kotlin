package com.example.kotlin.ui.note

import com.example.kotlin.data.entity.Note
import com.example.kotlin.ui.base.BaseViewState


class NoteViewState(val note: Note? = null, error: Throwable? = null) :
    BaseViewState<Note?>(note, error)