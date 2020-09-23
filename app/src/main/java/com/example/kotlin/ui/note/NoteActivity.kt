package com.example.kotlin.ui.note

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import com.example.kotlin.R
import com.example.kotlin.comon.format
import com.example.kotlin.comon.getColorInt
import com.example.kotlin.data.entity.Note
import com.example.kotlin.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_note.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class NoteActivity : BaseActivity<NoteViewState.Data>() {


    companion object {
        private val EXTRA_NOTE = NoteActivity::class.java.name + "extra.NOTE"

        fun start(context: Context, noteId: String?) =
            context.startActivity<NoteActivity>(EXTRA_NOTE to noteId)
    }

    private var backColor: Note.Color = Note.Color.WHITE
    private var note: Note? = null


    override val model: NoteViewModel by viewModel()
    override val layoutRes = R.layout.activity_note


    val textChangeListener = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            saveNote()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val noteId = intent.getStringExtra(EXTRA_NOTE)

        noteId?.let { id ->
            model.loadNote(id)
        } ?: let {
            supportActionBar?.title = getString(R.string.new_note_title)
        }
        initView()
        colorPicker.onColorClickListener = {
            backColor = it
            setToolbarColor(it)
            saveNote()
        }
    }


    private fun setToolbarColor(color: Note.Color) {
        toolbar.setBackgroundColor(color.getColorInt(this))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean =
        menuInflater.inflate(R.menu.note_menu, menu).let { true }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> super.onBackPressed().let { true }
        R.id.palette -> togglePalette().let { true }
        R.id.delete -> deleteNote().let { true }
        else -> super.onOptionsItemSelected(item)
    }

    private fun togglePalette() {
        if (!colorPicker.isOpen) colorPicker.open() else colorPicker.close()
    }

    private fun deleteNote() {
        alert {
            messageResource = R.string.delete_dialog_message
            negativeButton(R.string.logout_cancel) { dialog -> dialog.dismiss() }
            positiveButton(R.string.ok_bth_title) { model.deleteNote() }
        }.show()
    }


    private fun initView() {

        note?.run {
            supportActionBar?.title = lastChanged.format()
            toolbar.setBackgroundColor(color.getColorInt(this@NoteActivity))

            removeEditListener()
            et_title.setText(title)
            et_body.setText(text)
            backColor = color

        }
        setEditListener()
    }

    private fun saveNote() {
        if (et_title.text == null || et_title.text!!.length < 3) return

        note = note?.copy(
            title = et_title.text.toString(),
            text = et_body.text.toString(),
            lastChanged = Date(),
            color = backColor
        ) ?: Note(
            UUID.randomUUID().toString(),
            et_title.text.toString(),
            et_body.text.toString(),
            backColor
        )

        note?.let {
            model.save(it)
        }
    }


    override fun onBackPressed() {
        if (colorPicker.isOpen) {
            colorPicker.close()
            return
        }
        super.onBackPressed()
    }

    override fun renderData(data: NoteViewState.Data) {
        if (data.isDeleted) finish()

        this.note = data.note
        supportActionBar?.title = note?.lastChanged?.format() ?: let {
            getString(R.string.new_note_title)
        }
        initView()
    }


    private fun setEditListener() {

        et_title.addTextChangedListener(textChangeListener)
        et_body.addTextChangedListener(textChangeListener)
    }

    private fun removeEditListener() {
        et_title.removeTextChangedListener(textChangeListener)
        et_body.removeTextChangedListener(textChangeListener)
    }


}


