package com.example.kotlin.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.kotlin.data.Repository
import com.example.kotlin.data.entity.Note
import com.example.kotlin.data.model.Result
import com.example.kotlin.ui.note.NoteViewModel
import com.example.kotlin.ui.note.NoteViewState
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class NoteViewModelTest {
    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val mockRepository = mockk<Repository>(relaxed = true)
    private val noteLiveData = MutableLiveData<Result>()

    private val testNote = Note("1", "title", "text")

    private lateinit var viewModel: NoteViewModel

    @Before
    fun setup() {
        clearAllMocks()
        every { mockRepository.getNoteById(testNote.id) } returns noteLiveData
        every { mockRepository.deleteNote(testNote.id) } returns noteLiveData
        viewModel = NoteViewModel(mockRepository)
    }

    @Test
    fun `loadNote should return NoteData`() {
        var result: NoteViewState.Data? = null
        val testData = NoteViewState.Data(false, testNote)
        viewModel.viewStateLiveData.observeForever {
            result = it?.data
        }
        viewModel.loadNote(testNote.id)
        noteLiveData.value = Result.Success(testNote)
        assertEquals(testData, result)
    }


    @Test
    fun `loadNote should return error`() {
        var result: Throwable? = null
        val testData = Throwable("error")
        viewModel.viewStateLiveData.observeForever {
            result = it?.error
        }
        viewModel.loadNote(testNote.id)
        noteLiveData.value = Result.Error(error = testData)
        assertEquals(testData, result)
    }


    @Test
    fun `deleteNote should return NoteData with isDeleted`() {
        var result: NoteViewState.Data? = null
        val testData = NoteViewState.Data(true)
        viewModel.viewStateLiveData.observeForever {
            result = it?.data
        }
        viewModel.save(testNote)
        viewModel.deleteNote()
        noteLiveData.value = Result.Success(null)
        assertEquals(testData, result)
    }


    @Test
    fun `delete should return error`() {
        var result: Throwable? = null
        val testData = Throwable("error")
        viewModel.viewStateLiveData.observeForever {
            result = it?.error
        }
        viewModel.save(testNote)
        viewModel.deleteNote()
        noteLiveData.value = Result.Error(error = testData)
        assertEquals(testData, result)
    }

    @Test
    fun `should save changed`() {
        viewModel.save(testNote)
        viewModel.onCleared()
        verify(exactly = 1) { mockRepository.saveNote(testNote) }
    }

}