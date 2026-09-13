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
class DashboardViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: AppRepository
    private lateinit var viewModel: DashboardViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mock(AppRepository::class.java)
        viewModel = DashboardViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchDashboard success returns list of entities`() = runTest {
        val sampleEntities = listOf(
            Entity(species = "Item 1", scientificName = "Type A", description = "Test Desc 1"),
            Entity(species = "Item 2", scientificName = "Type B", description = "Test Desc 2")
        )
        val dashboardResponse = DashboardResponse(entities = sampleEntities, entityTotal = 2)

        `when`(repository.getDashboard("KEY123456")).thenReturn(Response.success(dashboardResponse))

        viewModel.fetchDashboard("KEY123456")
        testDispatcher.scheduler.advanceUntilIdle()

        val result = viewModel.entities.value
        assertTrue(result?.isSuccess == true)
        assertEquals(2, result?.getOrNull()?.size)
        assertEquals("Item 1", result?.getOrNull()?.get(0)?.species)
    }

    @Test
    fun `fetchDashboard with error response updates entities with failure`() = runTest {
        val errorResponse = Response.error<DashboardResponse>(
            404,
            okhttp3.ResponseBody.create(null, "")
        )

        `when`(repository.getDashboard("BADKEY")).thenReturn(errorResponse)

        viewModel.fetchDashboard("BADKEY")
        testDispatcher.scheduler.advanceUntilIdle()

        val result = viewModel.entities.value
        assertTrue(result?.isFailure == true)
        assertEquals("Failed to fetch dashboard (404)", result?.exceptionOrNull()?.message)
    }

    @Test
    fun `fetchDashboard with network exception updates entities with failure`() = runTest {
        `when`(repository.getDashboard("KEY123456"))
            .thenThrow(RuntimeException("timeout"))

        viewModel.fetchDashboard("KEY123456")
        testDispatcher.scheduler.advanceUntilIdle()

        val result = viewModel.entities.value
        assertTrue(result?.isFailure == true)
        assertTrue(result?.exceptionOrNull()?.message?.contains("Network error") == true)
    }
}