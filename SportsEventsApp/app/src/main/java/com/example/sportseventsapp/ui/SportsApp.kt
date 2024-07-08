package com.example.sportseventsapp.ui

import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
        LazyColumn(modifier = Modifier.padding(it)) {
            items(sportsEvents) { sport ->
                SportItem(sport, favoriteEvents, viewModel)
            }
        }
    }
}

@Composable
fun SportItem(sport: Sport, favoriteEvents: List<FavoriteEvent>, viewModel: SportsEventsViewModel) {
    var isExpanded by remember { mutableStateOf(true) }
    val favoriteEventIds = favoriteEvents.map { it.id }

    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray)
                .clickable { isExpanded = !isExpanded }
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = sport.name.uppercase(),
                style = MaterialTheme.typography.headlineSmall.copy(color = Color.Red),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { /* Handle favorite toggle */ }) {
                Icon(
                    imageVector = Icons.Filled.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = Color.Gray
                )
            }
            Switch(
                checked = isExpanded,
                onCheckedChange = { isExpanded = it }
            )
        }

        if (isExpanded) {
            val upcomingEvents = sport.sportEvents.filter { event ->
                val now = Date().time
                val startTimeInMillis = event.startTime.toLong() * 1000
                startTimeInMillis > now
            }
            Column(modifier = Modifier.background(Color.DarkGray).padding(8.dp)) {
                upcomingEvents.forEach { event ->
                    EventItem(event, favoriteEventIds.contains(event.id), viewModel)
                }
            }
        }
    }
}

@Composable
fun EventItem(sportEvent: SportEvent, isFavorite: Boolean, viewModel: SportsEventsViewModel) {
    val favoriteIcon = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder
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
                text = formattedStartTime,
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
                Icon(imageVector = favoriteIcon, contentDescription = "Favorite", tint = Color.White)
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
                text = "vs",
                style = MaterialTheme.typography.bodySmall.copy(color = Color.Red, fontSize = 14.sp),
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