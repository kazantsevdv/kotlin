package com.example.kotlin

import android.app.Application
import com.example.kotlin.di.appModule
import com.example.kotlin.di.mainModule
import com.example.kotlin.di.noteModule
import com.example.kotlin.di.splashModule

import org.koin.android.ext.android.startKoin


class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(appModule, splashModule, mainModule, noteModule))
    }
}
