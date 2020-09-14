package com.example.kotlin.ui.note

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.MenuItem
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin.R
import com.example.kotlin.comon.format
import com.example.kotlin.comon.getColorInt
import com.example.kotlin.data.entity.Note
import com.example.kotlin.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_note.*
import java.util.*

class NoteActivity : BaseActivity<Note?, NoteViewState>() {

    companion object {
        private val EXTRA_NOTE = NoteActivity::class.java.name + "extra.NOTE"
        fun start(context: Context, noteId: String? = null) =
            Intent(context, NoteActivity::class.java).run {
                noteId?.let {
                    putExtra(EXTRA_NOTE, noteId)
                }
                context.startActivity(this)
            }
    }

    private var note: Note? = null
    override val viewModel: NoteViewModel by lazy {
        ViewModelProvider(this).get(NoteViewModel::class.java)
    }
    override val layoutRes = R.layout.activity_note
    val afterTextChanged: (text: Editable?) -> Unit = {
        saveNote()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val noteId = intent.getStringExtra(EXTRA_NOTE)

        noteId?.let { id ->
            viewModel.loadNote(id)
        } ?: let {
            supportActionBar?.title = getString(R.string.new_note_title)
        }
        initView()
        et_title.addTextChangedListener(afterTextChanged = afterTextChanged)
        et_body.addTextChangedListener(afterTextChanged = afterTextChanged)
    }

    override fun renderData(data: Note?) {
        this.note = data
        supportActionBar?.title = note?.let { note ->
            note.lastChanged.format()
        } ?: let {
            getString(R.string.new_note_title)
        }

        initView()
    }


    private fun initView() {
        note?.run {
            et_title.setText(title)
            et_body.setText(text)
            toolbar.setBackgroundColor(color.getColorInt(this@NoteActivity))
        }
    }

    private fun saveNote() {
        if (et_title.text == null || et_title.text!!.length < 3) return

        note = note?.copy(
            title = et_title.text.toString(),
            text = et_body.text.toString(),
            lastChanged = Date()
        ) ?: Note(UUID.randomUUID().toString(), et_title.text.toString(), et_body.text.toString())

        note?.let {
            viewModel.save(it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}


