package com.example.sportseventsapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.sportseventsapp.model.FavoriteEvent
import com.example.sportseventsapp.model.Sport
import com.example.sportseventsapp.model.SportEvent
import com.example.sportseventsapp.repository.SportsRepository
import com.example.sportseventsapp.viewmodel.SportsEventsViewModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class SportsEventsViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var repository: SportsRepository
    private lateinit var viewModel: SportsEventsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        every { repository.sports } returns MutableStateFlow(emptyList())
        every { repository.favoriteEvents } returns MutableStateFlow(emptyList())
        coEvery { repository.refreshSportsEvents() } just Runs
        coEvery { repository.refreshFavoriteEvents() } just Runs
        viewModel = SportsEventsViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initializeSportsEvents sets loading true then false`() = runTest {
        // Given the initial state
        assertTrue(viewModel.loading.value)

        // When initializing sports events
        viewModel.initializeSportsEvents()

        // Wait until all coroutines finish
        advanceUntilIdle()

        // Then loading should be false
        assertFalse(viewModel.loading.value)
    }

    @Test
    fun `addFavorite calls repository addFavorite`() = runTest {
        val sportEvent = SportEvent(id = "1", sportId = "1", name = "Sample Event", startTime = 1630863600)
        coEvery { repository.addFavorite(any()) } just Runs

        viewModel.addFavorite(sportEvent)

        coVerify { repository.addFavorite(any()) }
    }

    @Test
    fun `removeFavorite calls repository removeFavorite`() = runTest {
        val sportEvent = SportEvent(id = "1", sportId = "1", name = "Sample Event", startTime = 1630863600)
        coEvery { repository.removeFavorite(any()) } just Runs

        viewModel.removeFavorite(sportEvent)

        coVerify { repository.removeFavorite(any()) }
    }

    @Test
    fun `sports state flow is correctly initialized`() {
        val sportsFlow: MutableStateFlow<List<Sport>> = MutableStateFlow(emptyList())
        every { repository.sports } returns sportsFlow

        viewModel = SportsEventsViewModel(repository)

        Assert.assertEquals(sportsFlow.value, viewModel.sports.value)
    }

    @Test
    fun `favoriteEvents state flow is correctly initialized`() {
        val favoriteEventsFlow: MutableStateFlow<List<FavoriteEvent>> = MutableStateFlow(emptyList())
        every { repository.favoriteEvents } returns favoriteEventsFlow

        viewModel = SportsEventsViewModel(repository)

        Assert.assertEquals(favoriteEventsFlow.value, viewModel.favoriteEvents.value)
    }
}