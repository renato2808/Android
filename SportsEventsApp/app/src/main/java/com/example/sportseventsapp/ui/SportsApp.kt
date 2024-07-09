package com.example.sportseventsapp.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sportseventsapp.model.FavoriteEvent
import com.example.sportseventsapp.model.Sport
import com.example.sportseventsapp.model.SportEvent
import com.example.sportseventsapp.viewmodel.SportsEventsViewModel
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SportsApp(viewModel: SportsEventsViewModel, context: Context) {
    val sportsEvents by viewModel.sportsEvents.collectAsState(initial = emptyList())
    val favoriteEvents by viewModel.favoriteEvents.collectAsState(initial = emptyList())
    val isLoading by viewModel.loading.collectAsState(initial = false)

    LaunchedEffect(Unit) {
        viewModel.initializeSportsEvents()
    }

    val appName = try {
        val appInfo = context.packageManager.getApplicationInfo(context.packageName, 0)
        context.packageManager.getApplicationLabel(appInfo).toString()
    } catch (e: Exception) {
        "APP NAME"  // Default fallback name
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(appName) })
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                sportsEvents.isEmpty() -> {
                    Text(
                        text = "No events available",
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.headlineSmall.copy(color = Color.Gray)
                    )
                }
                else -> {
                    LazyColumn(modifier = Modifier.padding(it)) {
                        items(sportsEvents) { sport ->
                            val isExpandedState = remember { mutableStateOf(false) }
                            val showFavoritesState = remember { mutableStateOf(false) }
                            SportItem(sport, favoriteEvents, viewModel, isExpandedState, showFavoritesState)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SportItem(
    sport: Sport,
    favoriteEvents: List<FavoriteEvent>,
    viewModel: SportsEventsViewModel,
    isExpandedState: MutableState<Boolean>,
    showFavoritesState: MutableState<Boolean>
) {
    val isExpanded by isExpandedState
    val showFavorites by showFavoritesState
    val favoriteEventIds = favoriteEvents.map { it.id }

    val upcomingEvents = sport.sportEvents.filter { event ->
        val now = Date().time
        val startTimeInMillis = event.startTime.toLong() * 1000
        startTimeInMillis > now
    }.sortedBy { event ->
        event.startTime
    }

    val favoriteUpcomingEvents = upcomingEvents.filter { event ->
        favoriteEventIds.contains(event.id)
    }

    val eventsToDisplay = if (showFavorites) favoriteUpcomingEvents else upcomingEvents

    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray)
                .clickable { isExpandedState.value = !isExpanded }
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = sport.name,
                style = MaterialTheme.typography.headlineSmall.copy(color = Color.Red),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Show Favorites",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Switch(
                checked = showFavorites,
                onCheckedChange = { showFavoritesState.value = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.Yellow,
                    uncheckedThumbColor = Color.Black,
                    checkedTrackColor = Color.Yellow.copy(alpha = 0.5f),
                    uncheckedTrackColor = Color.Black.copy(alpha = 0.5f)
                )
            )
            IconButton(
                onClick = { isExpandedState.value = !isExpanded },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    tint = Color.Black
                )
            }
        }

        if (isExpanded) {
            Column(modifier = Modifier.background(Color.DarkGray).padding(8.dp)) {
                eventsToDisplay.forEach { event ->
                    EventItem(event, favoriteEventIds.contains(event.id), viewModel)
                }
            }
        }
    }
}

@Composable
fun EventItem(sportEvent: SportEvent, isFavorite: Boolean, viewModel: SportsEventsViewModel) {
    val favoriteIcon = if (isFavorite) Icons.Filled.Star else Icons.Outlined.Star
    val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    val formattedStartTime = dateFormat.format(Date(sportEvent.startTime.toLong() * 1000))

    // Split the name into two competitors
    val competitors = sportEvent.name.split("-")
    val competitor1 = competitors.getOrNull(0) ?: "Unknown"
    val competitor2 = competitors.getOrNull(1) ?: "Unknown"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray)
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text =  "Starts at: $formattedStartTime",
                style = MaterialTheme.typography.bodySmall.copy(color = Color.White, fontSize = 16.sp)
            )
            IconButton(
                onClick = {
                    if (isFavorite) {
                        viewModel.removeFavorite(sportEvent)
                    } else {
                        viewModel.addFavorite(sportEvent)
                    }
                }
            ) {
                Icon(
                    imageVector = favoriteIcon,
                    contentDescription = "Favorite",
                    tint = if (isFavorite) Color.Yellow else Color.Black
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = competitor1,
                style = MaterialTheme.typography.bodySmall.copy(color = Color.White, fontSize = 12.sp),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            Text(
                text = "VS",
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.Red, fontSize = 14.sp),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            Text(
                text = competitor2,
                style = MaterialTheme.typography.bodySmall.copy(color = Color.White, fontSize = 12.sp),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
        // Countdown Timer
        CountdownTimer(startTime = sportEvent.startTime)
    }
}

@Composable
fun CountdownTimer(startTime: Int) {
    var timeLeft by remember { mutableStateOf(startTime - System.currentTimeMillis() / 1000) }
    LaunchedEffect(startTime) {
        while (timeLeft > 0) {
            delay(1000L)
            timeLeft = startTime - System.currentTimeMillis() / 1000
        }
    }
    val days = timeLeft / (60 * 60 * 24)
    val hours = (timeLeft % (60 * 60 * 24)) / (60 * 60)
    val minutes = (timeLeft % (60 * 60)) / 60
    val seconds = timeLeft % 60

    Text(text = String.format("%d days %02d:%02d:%02d", days, hours, minutes, seconds))
}