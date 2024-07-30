package com.example.sportseventsapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.sportseventsapp.model.FavoriteEvent
import com.example.sportseventsapp.model.Sport
import com.example.sportseventsapp.model.SportEvent
import com.example.sportseventsapp.ui.CountdownTimer
import com.example.sportseventsapp.ui.SportsApp
import com.example.sportseventsapp.viewmodel.SportsEventsViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SportsAppTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: SportsEventsViewModel

    @Before
    fun setUp() {
        viewModel = mockk(relaxed = true)
    }

    @Test
    fun sportsApp_DisplayLoading() {
        every { viewModel.loading } returns MutableStateFlow(true)

        composeTestRule.setContent {
            SportsApp(viewModel = viewModel)
        }

        // Verify that CircularProgressIndicator is displayed
        composeTestRule.onNodeWithTag("ProgressIndicator").assertExists()
    }

    @Test
    fun sportsApp_DisplayNoEventsAvailable() {
        every { viewModel.loading } returns MutableStateFlow(false)
        every { viewModel.sports } returns MutableStateFlow(emptyList())

        composeTestRule.setContent {
            SportsApp(viewModel = viewModel)
        }

        // Verify that "No events available" text is displayed
        composeTestRule.onNodeWithText("No events available").assertExists()
    }

    @Test
    fun sportsApp_DisplayEvents() {
        val sportsEvents = listOf(
            Sport(id = "1", name = "Football", sportEvents = emptyList()),
            Sport(id = "2", name = "Basketball", sportEvents = emptyList())
        )
        every { viewModel.loading } returns MutableStateFlow(false)
        every { viewModel.sports } returns MutableStateFlow(sportsEvents)

        composeTestRule.setContent {
            SportsApp(viewModel = viewModel)
        }

        // Verify that the list items are displayed correctly
        sportsEvents.forEach { sport ->
            composeTestRule.onNodeWithText(sport.name).assertExists()
        }
    }

    @Test
    fun sportsApp_FilterFavorites() {
        // Define sports events and favorite events
        val sportsEvents = listOf(
            Sport(id = "1", name = "Football", sportEvents = listOf(
                SportEvent(id = "1", name = "Football1 - Football1", startTime = 1655721600)
            )),
            Sport(id = "2", name = "Basketball", sportEvents = listOf(
                SportEvent(id = "2", name = "Basketball1 - Basketball1", startTime = 1655721600)
            ))
        )
        val favoriteEvents = listOf(
            FavoriteEvent(id = "1", name = "Football1 - Football1", sportId = "1", startTime = 1630863600)
        )

        // Mock view model
        every { viewModel.loading } returns MutableStateFlow(false)
        every { viewModel.sports } returns MutableStateFlow(sportsEvents)
        every { viewModel.favoriteEvents } returns MutableStateFlow(favoriteEvents)

        // Set the Composable content
        composeTestRule.setContent {
            SportsApp(viewModel = viewModel)
        }

        // Toggle the filter switch for "Football" sport to show only favorite events
        composeTestRule.onNodeWithTag("SportItem_1").performClick()
        // Toggle the filter switch for "Football" sport to show only favorite events
        composeTestRule.onNodeWithTag("SportItem_2").performClick()

        // Wait for UI to settle
        composeTestRule.waitForIdle()

        // Print the UI hierarchy to log
        composeTestRule.onRoot().printToLog("TAG")

        // Verify that the sport items are displayed
        composeTestRule.onNodeWithText("Football1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Basketball1").assertIsDisplayed()

        // Toggle the filter switch for "Football" sport to show only favorite events
        composeTestRule.onNodeWithTag("FavoriteSwitch_1").performClick()

        // Verify that only the favorite event is displayed after toggling the switch
        composeTestRule.onNodeWithText("Football1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Basketball1").assertDoesNotExist()
    }

    @Test
    fun countdownTimer_DisplayCorrectTime() {
        val startTime = (System.currentTimeMillis() / 1000).toInt() + 10
        composeTestRule.setContent {
            CountdownTimer(startTime = startTime)
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("0 days 00:00:10").assertExists()
    }
}