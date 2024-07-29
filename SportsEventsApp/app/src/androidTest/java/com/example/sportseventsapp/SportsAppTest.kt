package com.example.sportseventsapp

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.sportseventsapp.ui.MainActivity
import com.example.sportseventsapp.ui.SportsApp
import com.example.sportseventsapp.ui.theme.SportsEventsAppTheme
import com.example.sportseventsapp.viewmodel.SportsEventsViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class SportsAppTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var viewModel: SportsEventsViewModel

    @Before
    fun setUp() {
        hiltRule.inject()

        composeTestRule.setContent {
            SportsApp(viewModel = viewModel)
        }

        // Verify that the list items display only favorite events
        composeTestRule.onNodeWithText("Football Match").assertExists()
        composeTestRule.onNodeWithText("Basketball Game").assertDoesNotExist()
    }

    @Test
    fun testComponentsDisplayed() {
        // Wait for the sports data to be loaded
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            viewModel.sports.value.isNotEmpty()
        }

        // Check if the app name is displayed in the TopAppBar
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.app_name))
            .assertIsDisplayed()

        // Check if the first sport item is displayed and clickable
        composeTestRule.onNodeWithText(viewModel.sports.value.first().name)
            .assertIsDisplayed()
            .performClick()

        // Check if the favorite switch is displayed and clickable
        composeTestRule.onNodeWithTag("FavoriteSwitch")
            .assertIsDisplayed()
            .performClick()

        // Check if the expand/collapse icon is displayed and clickable
        composeTestRule.onNodeWithTag("ExpandIcon")
            .assertIsDisplayed()
            .performClick()

        // Check if the event item is displayed after expanding
        composeTestRule.onNodeWithTag("EventItem")
            .assertIsDisplayed()

        // Perform a click on the event item
        composeTestRule.onNodeWithTag("EventItem")
            .performClick()

        // Check if the favorite icon is displayed and clickable
        composeTestRule.onNodeWithTag("FavoriteIcon")
            .assertIsDisplayed()
            .performClick()
    }
}