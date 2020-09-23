package com.example.kotlin.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kotlin.R
import com.example.kotlin.data.entity.Note
import com.example.kotlin.ui.base.BaseActivity
import com.example.kotlin.ui.note.NoteActivity
import com.example.kotlin.ui.splash.SplashActivity
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<List<Note>?>() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    override val model: MainViewModel by viewModel()
//    override val viewModel: BaseViewModel<List<Note>?, MainViewState> by lazy {
//        ViewModelProvider(this).get(MainViewModel::class.java)
//    }

    override val layoutRes = R.layout.activity_main

    lateinit var adapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        rv_notes.layoutManager = GridLayoutManager(this, 2)
        adapter = NotesAdapter { note ->
            openNoteScreen(note)
        }
        rv_notes.adapter = adapter
        fab.setOnClickListener {
            openNoteScreen(null)

        }
    }

    private fun openNoteScreen(note: Note?) {
        NoteActivity.start(this, note?.id)
    }

    override fun renderData(data: List<Note>?) {
        data?.let {
            adapter.notes = it
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean =
        MenuInflater(this).inflate(R.menu.main, menu).let { true }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.logout -> showLogoutDialog().let { true }
        else -> false
    }


    private fun showLogoutDialog() {
        alert {
            titleResource = R.string.logout_title
            messageResource = R.string.logout_message
            positiveButton(R.string.logout_ok) { onLogout() }
            negativeButton(R.string.logout_cancel) { dialog -> dialog.dismiss() }
        }.show()
    }

    fun onLogout() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                startActivity(Intent(this, SplashActivity::class.java))
                finish()
            }
    }
}
