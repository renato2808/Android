package com.example.sportseventsapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.sportseventsapp.SportsEventsApp
import com.example.sportseventsapp.viewmodel.SportsEventsViewModel
import com.example.sportseventsapp.ui.theme.SportsEventsAppTheme


class MainActivity : ComponentActivity() {

    private val viewModel: SportsEventsViewModel by viewModels {
        SportsEventsViewModel.SportsEventsViewModelFactory(
            (this.applicationContext as? SportsEventsApp)?.repository,
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SportsEventsAppTheme {
                SportsApp(viewModel, this)
            }
        }
    }
}