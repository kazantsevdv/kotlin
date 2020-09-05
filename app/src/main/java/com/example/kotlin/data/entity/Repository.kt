package com.example.kotlin.data.entity

import android.graphics.Color

object Repository {
    val notes: List<Note> = listOf(
        Note("Заметка1", "Текст заметки 1", Color.BLUE),
        Note("Заметка2", "Текст заметки 2", Color.CYAN),
        Note("Заметка3", "Текст заметки 3", Color.DKGRAY),
        Note("Заметка4", "Текст заметки 4"),
        Note("Заметка5", "Текст заметки 5")
    )
}