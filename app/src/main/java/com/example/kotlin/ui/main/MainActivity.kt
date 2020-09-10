package com.example.kotlin.ui.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kotlin.R
import com.example.kotlin.data.entity.Note
import com.example.kotlin.ui.base.BaseActivity
import com.example.kotlin.ui.base.BaseViewModel
import com.example.kotlin.ui.note.NoteActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<List<Note>?, MainViewState>() {


    override val viewModel: BaseViewModel<List<Note>?, MainViewState> by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override val layoutRes = R.layout.activity_main

    lateinit var adapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        rv_notes.layoutManager = GridLayoutManager(this, 2)
        adapter = NotesAdapter { note ->
            NoteActivity.start(this, note.id)
        }
        rv_notes.adapter = adapter
        fab.setOnClickListener {
            NoteActivity.start(this)
        }
    }

    override fun renderData(data: List<Note>?) {
        data?.let {
            adapter.notes = it
        }
    }
}