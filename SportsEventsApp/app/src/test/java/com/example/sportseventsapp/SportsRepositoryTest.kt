package com.example.sportseventsapp

import android.app.Application
import android.util.Log
import com.example.sportseventsapp.api.SportsApiService
import com.example.sportseventsapp.data.FavoriteEventDao
import com.example.sportseventsapp.data.SportsDao
import com.example.sportseventsapp.data.SportsEventsDatabase
import com.example.sportseventsapp.model.FavoriteEvent
import com.example.sportseventsapp.model.SportEvent
import com.example.sportseventsapp.repository.SportsRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SportsRepositoryTest {

    private lateinit var apiService: SportsApiService
    private lateinit var database: SportsEventsDatabase
    private lateinit var repository: SportsRepository
    private lateinit var application: Application

    @Before
    fun setup() {
        apiService = mockk()
        database = mockk()
        application = mockk()
        repository = SportsRepository(application, database, Dispatchers.Main)
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0

        // Initialize main dispatcher for testing
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        // Cleanup
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun testRefreshSportsEventsSuccess() = runBlockingTest {
        // Mock ApiService response
        coEvery { apiService.getSports().execute() } returns mockk {
            every { isSuccessful } returns true
        }

        // Mock Database DAO response
        val sportsDao = mockk<SportsDao>()
        every { database.sportsDao() } returns sportsDao
        coEvery { sportsDao.getAllSports() } returns emptyList()
        coEvery { sportsDao.deleteAll() } returns Unit
        coEvery { sportsDao.insertSport(any()) } returns Unit

        repository.refreshSportsEvents()

        // Verify that 8 sports are returning from the server call
        assertEquals(8, repository.sports.value.size)
    }

    @Test
    fun testAddFavorite() = runBlockingTest {
        // Mock Database DAO response
        val favoriteEvent = FavoriteEvent(id = "1", name = "Sample Event", sportId = "1", startTime = 1630863600)
        val favoriteEventDao = mockk<FavoriteEventDao>()
        val favoriteEvents = listOf(
            favoriteEvent
        )
        every { database.favoriteEventDao() } returns favoriteEventDao
        coEvery { favoriteEventDao.insert(favoriteEvent) } returns Unit
        coEvery { favoriteEventDao.getAll() } returns favoriteEvents

        repository.addFavorite(favoriteEvent)

        assertEquals(1, repository.favoriteEvents.value.size)
        assertEquals(favoriteEvent, repository.favoriteEvents.value[0])
    }

    @Test
    fun testRemoveFavorite() = runBlockingTest {
        // Mock Database DAO response
        val favoriteEvent = FavoriteEvent(id = "1", name = "Sample Event", sportId = "1", startTime = 1630863600)
        val favoriteEventDao = mockk<FavoriteEventDao>()

        every { database.favoriteEventDao() } returns favoriteEventDao
        coEvery { favoriteEventDao.deleteById(favoriteEvent.id) } returns 0
        coEvery { favoriteEventDao.getAll() } returns emptyList()

        repository.removeFavorite(favoriteEvent)

        assertEquals(0, repository.favoriteEvents.value.size)
        assertEquals(emptyList<SportEvent>(), repository.favoriteEvents.value)
    }
}