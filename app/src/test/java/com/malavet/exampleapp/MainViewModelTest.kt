package com.malavet.exampleapp

import android.content.ClipData
import com.malavet.exampleapp.data.SchoolSATRepository
import com.malavet.exampleapp.data.models.School
import com.malavet.exampleapp.ui.MainViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi

class CoroutineTestRule(
    val testDispatcher: TestDispatcher = StandardTestDispatcher()
) : TestWatcher() {

    @Before
    override fun starting(description: Description?) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }
}

class MainViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()
    val dataRepository = mockk<SchoolSATRepository>()
    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        viewModel = MainViewModel(dataRepository)
    }

    @Test
    fun testGetSchoolList() = runBlockingTest {
        val mockData = School("a","")
        coEvery { dataRepository.schools() } returns emptyList()

        viewModel.getSchools()

        assertEquals(mockData, emptyList<School>())
    }
}