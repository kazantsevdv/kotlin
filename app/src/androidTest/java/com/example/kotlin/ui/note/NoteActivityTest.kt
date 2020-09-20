package com.example.kotlin.ui.note

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.example.kotlin.R
import com.example.kotlin.comon.getColorInt
import com.example.kotlin.data.entity.Note
import io.mockk.*
import junit.framework.Assert.assertTrue
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.loadKoinModules
import org.koin.standalone.StandAloneContext.stopKoin


class NoteActivityTest {

    @get:Rule
    val activityTestRule = ActivityTestRule(NoteActivity::class.java, true, false)
    private val viewModel: NoteViewModel = spyk(NoteViewModel(mockk()))
    private val viewStateLiveData = MutableLiveData<NoteViewState>()
    private val testNote = Note("333", "title", "body")

    @Before
    fun setup() {

        loadKoinModules(
            listOf(
                module {
                    viewModel(override = true) { viewModel }
                })
        )
        every { viewModel.getViewState() } returns viewStateLiveData
        every { viewModel.loadNote(any()) } just runs
        every { viewModel.save(any()) } just runs
        every { viewModel.deleteNote() } just runs

        Intent().apply {
            putExtra(NoteActivity::class.java.name + "extra.NOTE_ID", testNote.id)
        }.let {
            activityTestRule.launchActivity(it)
        }
    }


    @After
    fun tearDown() {
        stopKoin()
    }


    @Test
    fun should_show_color_picker() {
        onView(withId(R.id.palette)).perform(click())

        onView(withId(R.id.colorPicker)).check(matches(isCompletelyDisplayed()))
    }

    @Test
    fun should_hide_color_picker() {
        onView(withId(R.id.palette)).perform(click()).perform(click())

        onView(withId(R.id.colorPicker)).check(matches(not(isDisplayed())))
    }

    @Test
    fun should_set_toolbar_color() {
        onView(withId(R.id.palette)).perform(click())
        onView(withTagValue(`is`(Note.Color.BLUE))).perform(click())

        val colorInt = Note.Color.BLUE.getColorInt(activityTestRule.activity)

        onView(withId(R.id.toolbar)).check { view, _ ->
            assertTrue(
                "toolbar background color does not match",
                (view.background as? ColorDrawable)?.color == colorInt
            )
        }
    }


    @Test
    fun should_show_note() {
        activityTestRule.launchActivity(null)
        viewStateLiveData.postValue(NoteViewState(NoteViewState.Data(note = testNote)))

        onView(withId(R.id.et_title)).check(matches(withText(testNote.title)))
        onView(withId(R.id.et_body)).check(matches(withText(testNote.text)))
    }

    @Test
    fun should_call_saveNote() {
        onView(withId(R.id.et_title)).perform(typeText(testNote.title))
        onView(withId(R.id.et_body)).perform(typeText(testNote.text))
        verify(timeout = 1000) { viewModel.save(any()) }
    }

    @Test
    fun should_call_deleteNote() {
        openActionBarOverflowOrOptionsMenu(activityTestRule.activity)
        onView(withText(R.string.delete_menu_title)).perform(click())
        onView(withText(R.string.ok_bth_title)).perform(click())
        verify { viewModel.deleteNote() }
    }

}