package com.example.kotlin.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.kotlin.data.Repository
import com.example.kotlin.data.entity.Note
import com.example.kotlin.data.model.Result
import com.example.kotlin.ui.main.MainViewModel
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MainViewModelTest {
    private val mockRepository = mockk<Repository>()
    private val notesLiveData = MutableLiveData<Result>()
    private lateinit var viewModel: MainViewModel

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        clearAllMocks()

        every { mockRepository.getNotes() } returns notesLiveData
        viewModel = MainViewModel(mockRepository)
    }

    @Test
    fun `should call getNotes once`() {
        verify(exactly = 1) { mockRepository.getNotes() }
    }

    @Test
    fun `should return error`() {
        var result: Throwable? = null
        val testData = Throwable("error")
        viewModel.getViewState().observeForever { result = it?.error }
        notesLiveData.value = Result.Error(testData)
        assertEquals(result, testData)
    }

    @Test
    fun `should return Notes`() {
        var result: List<Note>? = null
        val testData = listOf(Note(id = "1"), Note(id = "2"))
        viewModel.getViewState().observeForever { result = it?.data }
        notesLiveData.value = Result.Success(testData)
        assertEquals(testData, result)
    }

    @Test
    fun `should remove observer`() {
        viewModel.onCleared()
        assertFalse(notesLiveData.hasObservers())
    }

}