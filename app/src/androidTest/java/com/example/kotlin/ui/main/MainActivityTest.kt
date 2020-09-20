package com.example.kotlin.ui.main


import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.kotlin.R
import com.example.kotlin.data.entity.Note
import com.example.kotlin.ui.note.NoteActivity
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.loadKoinModules
import org.koin.standalone.StandAloneContext.stopKoin


class MainActivityTest {
    @get:Rule
    val activityRule = IntentsTestRule(MainActivity::class.java, true, false)

    private val EXTRA_NOTE = NoteActivity::class.java.name + "extra.NOTE"

    private val viewModel: MainViewModel = mockk(relaxed = true)

    private val viewStateLiveData = MutableLiveData<MainViewState>()
    private val testNotes = listOf(
        Note("333", "title", "body"),
        Note("444", "title1", "body1"),
        Note("555", "title2", "body2")
    )

    @Before
    fun setup() {
        loadKoinModules(
            listOf(module {
                viewModel(override = true) { viewModel }
            })
        )

        every { viewModel.getViewState() } returns viewStateLiveData
        every { viewModel.onCleared() } just runs
        activityRule.launchActivity(null)
        viewStateLiveData.postValue(MainViewState(notes = testNotes))
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun check_data_is_displayed() {
        onView(withId(R.id.rv_notes)).perform(scrollToPosition<NotesAdapter.ViewHolder>(1))
        onView(withText(testNotes[0].text)).check(matches(isDisplayed()))
    }

    @Test
    fun check_detail_activity_intent_sent() {
        onView(withId(R.id.rv_notes))
            .perform(actionOnItemAtPosition<NotesAdapter.ViewHolder>(1, click()))

        intended(
            allOf(
                hasComponent(NoteActivity::class.java.name),
                hasExtra(EXTRA_NOTE, testNotes[1].id)
            )
        )
    }
}