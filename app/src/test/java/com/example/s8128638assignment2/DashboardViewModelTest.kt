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
            Entity(property1 = "Item 1", property2 = "Type A", description = "Test Desc 1"),
            Entity(property1 = "Item 2", property2 = "Type B", description = "Test Desc 2")
        )
        val dashboardResponse = DashboardResponse(entities = sampleEntities, entityTotal = 2)

        `when`(repository.getDashboard("KEY123456")).thenReturn(Response.success(dashboardResponse))

        viewModel.fetchDashboard("KEY123456")
        testDispatcher.scheduler.advanceUntilIdle()

        val result = viewModel.entities.value
        assertTrue(result?.isSuccess == true)
        assertEquals(2, result?.getOrNull()?.size)
        assertEquals("Item 1", result?.getOrNull()?.get(0)?.property1)
    }
}