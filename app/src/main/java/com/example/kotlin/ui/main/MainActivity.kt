package com.example.kotlin.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kotlin.R
import com.example.kotlin.ui.note.NoteActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    private lateinit var notesAdapter: NotesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        notesAdapter = NotesAdapter { NoteActivity.start(this, it) }
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        rv_notes.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = notesAdapter
        }
        fab.setOnClickListener { NoteActivity.start(this) }
        viewModel.viewState().observe(this, { notesAdapter.notes = it.notes })

    }

}