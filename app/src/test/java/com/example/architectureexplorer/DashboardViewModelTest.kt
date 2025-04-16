package com.example.architectureexplorer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.architectureexplorer.data.model.DashboardResponse
import com.example.architectureexplorer.data.model.Entity
import com.example.architectureexplorer.data.remote.ApiService
import com.example.architectureexplorer.ui.dashboard.DashboardViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.*
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
class DashboardViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private val apiService = mock<ApiService>()
    private lateinit var viewModel: DashboardViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = DashboardViewModel(apiService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getDashboard success emits entity list`() = runTest(testDispatcher) {
        val keypass = "test-key"
        val entities = listOf(
            Entity(
                name = "Eiffel Tower",
                architect = "Gustave Eiffel",
                location = "Paris, France",
                yearCompleted = 1889,
                style = "Structural Expressionism",
                height = 324,
                description = "Iconic tower in Paris"
            )
        )
        whenever(apiService.getDashboard(keypass)).thenReturn(
            DashboardResponse(entities, entities.size)
        )

        viewModel.getDashboard(keypass)
        advanceUntilIdle()

        val result = viewModel.dashboard.value
        assertTrue(result?.isSuccess == true)
        assertEquals(entities, result?.getOrNull())
    }

    @Test
    fun `getDashboard failure emits error`() = runTest(testDispatcher) {
        val keypass = "invalid"
        whenever(apiService.getDashboard(keypass)).thenThrow(RuntimeException("Unauthorized"))

        viewModel.getDashboard(keypass)
        advanceUntilIdle()

        val result = viewModel.dashboard.value
        assertTrue(result?.isFailure == true)
        assertEquals("Unauthorized", result?.exceptionOrNull()?.message)
    }
}
