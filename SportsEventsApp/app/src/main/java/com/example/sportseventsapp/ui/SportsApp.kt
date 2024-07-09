package com.example.sportseventsapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sportseventsapp.R
import com.example.sportseventsapp.model.FavoriteEvent
import com.example.sportseventsapp.model.Sport
import com.example.sportseventsapp.model.SportEvent
import com.example.sportseventsapp.viewmodel.SportsEventsViewModel
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SportsApp(viewModel: SportsEventsViewModel) {
    val sports by viewModel.sports.collectAsState(initial = emptyList())
    val favoriteEvents by viewModel.favoriteEvents.collectAsState(initial = emptyList())
    val isLoading by viewModel.loading.collectAsState(initial = false)

    LaunchedEffect(Unit) {
        viewModel.initializeSportsEvents()
    }

    val expandedStates = remember { mutableStateMapOf<String, MutableState<Boolean>>() }
    val favoriteSwitchStates = remember { mutableStateMapOf<String, MutableState<Boolean>>() }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.app_name)) })
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                sports.isEmpty() -> {
                    Text(
                        text = stringResource(R.string.no_events_available),
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.headlineSmall.copy(color = Color.Gray)
                    )
                }

                else -> {
                    LazyColumn(modifier = Modifier.padding(it)) {
                        items(sports) { sport ->
                            val isExpandedState = expandedStates.getOrPut(sport.id) { mutableStateOf(false) }
                            val showFavoritesState = favoriteSwitchStates.getOrPut(sport.id) { mutableStateOf(false) }
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
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isExpandedState.value = !isExpanded }
                .padding(horizontal = 16.dp, vertical = 8.dp),
            color = MaterialTheme.colorScheme.primary,
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 4.dp,
            shadowElevation = 8.dp
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = sport.name.lowercase().split(" ")
                        .joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } },
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Switch(
                    checked = showFavorites,
                    onCheckedChange = { showFavoritesState.value = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color(0xFFFFD700), // Darker yellow color
                        uncheckedThumbColor = Color.Black,
                        checkedTrackColor = Color(0xFFFFD700).copy(alpha = 0.5f),
                        uncheckedTrackColor = Color.Black.copy(alpha = 0.5f)
                    ),
                    thumbContent = {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(SwitchDefaults.IconSize).graphicsLayer {
                                shape = CircleShape
                                clip = true
                            }
                        )
                    }
                )
                IconButton(
                    onClick = { isExpandedState.value = !isExpanded },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = stringResource(if (isExpanded) R.string.collapse else R.string.expand),
                        tint = Color.White
                    )
                }
            }
        }

        if (isExpanded && eventsToDisplay.isNotEmpty()) {
            Column(modifier = Modifier
                .background(Color.DarkGray)
                .padding(8.dp)) {
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
    val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    val dateFormat = SimpleDateFormat("MM/dd", Locale.getDefault())
    val formattedStartTime = timeFormat.format(Date(sportEvent.startTime.toLong() * 1000))
    val formattedStartDate = dateFormat.format(Date(sportEvent.startTime.toLong() * 1000))

    // Split the name into two competitors
    val competitors = sportEvent.name.split("-")
    val competitor1 = competitors.getOrNull(0) ?: stringResource(R.string.unknown)
    val competitor2 = competitors.getOrNull(1) ?: stringResource(R.string.unknown)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        color = MaterialTheme.colorScheme.surface,
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 4.dp,
        shadowElevation = 8.dp
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.starts_at, "$formattedStartDate $formattedStartTime"),
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
                        contentDescription = stringResource(R.string.favorite),
                        tint = if (isFavorite) Color(0xFFFFD700) else Color.Black
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
                    text = stringResource(R.string.vs),
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.Red),
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