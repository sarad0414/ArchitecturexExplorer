package com.example.architectureexplorer

import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.architectureexplorer.data.model.LoginResponse
import com.example.architectureexplorer.data.remote.ApiService
import com.example.architectureexplorer.ui.login.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private val apiService = mock<ApiService>()
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = LoginViewModel(apiService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login success emits keypass`() = runTest(testDispatcher) {
        val expectedKey = "test-key"
        val credentials = mapOf("username" to "Sarad", "password" to "s4679227")
        whenever(apiService.login("sydney", credentials))
            .thenReturn(LoginResponse(expectedKey))

        viewModel.login("sydney", "Sarad", "s4679227")
        advanceUntilIdle()

        val result = viewModel.loginResult.value
        assertTrue(result?.isSuccess == true)
        assertEquals(expectedKey, result?.getOrNull())
    }

    @Test
    fun `login failure emits error`() = runTest(testDispatcher) {
        val credentials = mapOf("username" to "wrong", "password" to "bad")
        whenever(apiService.login("sydney", credentials))
            .thenThrow(RuntimeException("Login failed"))

        viewModel.login("sydney", "wrong", "bad")
        advanceUntilIdle()

        val result = viewModel.loginResult.value
        assertTrue(result?.isFailure == true)
        assertEquals("Login failed", result?.exceptionOrNull()?.message)
    }
}
