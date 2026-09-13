package com.example.s8128638assignment2

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: AppRepository
    private lateinit var viewModel: AuthViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mock(AppRepository::class.java)
        viewModel = AuthViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login with empty credentials updates loginResult with failure`() {
        viewModel.login("", "")

        val result = viewModel.loginResult.value
        assertTrue(result?.isFailure == true)
        assertEquals("Please enter both username and password", result?.exceptionOrNull()?.message)
    }

    @Test
    fun `login success updates loginResult with keypass`() = runTest {
        val authRequest = AuthRequest("student", "password123")
        val authResponse = AuthResponse("KEY123456")

        `when`(repository.login(authRequest)).thenReturn(Response.success(authResponse))

        viewModel.login("student", "password123")
        testDispatcher.scheduler.advanceUntilIdle()

        val result = viewModel.loginResult.value
        assertTrue(result?.isSuccess == true)
        assertEquals("KEY123456", result?.getOrNull())
    }

    @Test
    fun `login with invalid credentials updates loginResult with failure`() = runTest {
        val errorResponse = Response.error<AuthResponse>(
            401,
            okhttp3.ResponseBody.create(null, "")
        )

        `when`(repository.login(AuthRequest("wrong", "wrong"))).thenReturn(errorResponse)

        viewModel.login("wrong", "wrong")
        testDispatcher.scheduler.advanceUntilIdle()

        val result = viewModel.loginResult.value
        assertTrue(result?.isFailure == true)
        assertEquals("Invalid credentials (401)", result?.exceptionOrNull()?.message)
    }

    @Test
    fun `login with network exception updates loginResult with failure`() = runTest {
        `when`(repository.login(AuthRequest("student", "password123")))
            .thenThrow(RuntimeException("timeout"))

        viewModel.login("student", "password123")
        testDispatcher.scheduler.advanceUntilIdle()

        val result = viewModel.loginResult.value
        assertTrue(result?.isFailure == true)
        assertTrue(result?.exceptionOrNull()?.message?.contains("Network error") == true)
    }
}