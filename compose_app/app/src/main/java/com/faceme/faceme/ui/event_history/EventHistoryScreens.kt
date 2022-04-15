package com.faceme.faceme.ui.event_history

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.amplifyframework.datastore.generated.model.AccountOwner
import com.faceme.faceme.R
import com.faceme.faceme.model.Event
import com.faceme.faceme.ui.rememberContentPaddingForScreen
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.isActive

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EventHistoryWithEventDetailsScreen(
    uiState: EventHistoryUiState.HasEvents,
    showTopAppBar: Boolean,
    onInteractWithHistory: () -> Unit,
    onInteractWithEventDetails: (String) -> Unit,
    openDrawer: () -> Unit,
    scaffoldState: ScaffoldState,
    eventHistoryLazyListState: LazyListState,
    eventDetailLazyListStates: Map<String, LazyListState>,
    modifier: Modifier = Modifier
) {
    EventHistoryScreenWithList(
        uiState = uiState,
        showTopAppBar = showTopAppBar,
        onInteractWithHistory = onInteractWithHistory,
        onInteractWithEventDetails = onInteractWithEventDetails,
        eventListLazyListState = eventHistoryLazyListState,
        openDrawer = openDrawer,
        scaffoldState = scaffoldState,
    ) {
        hasEventsUiState, contentModifier ->
        val contentPadding = rememberContentPaddingForScreen(additionalTop = 8.dp)
        Row(contentModifier) {
            EventHistoryList(
                eventHistory = uiState.eventFeed.allEvents,
                onSelectEvent = onInteractWithEventDetails,
                modifier = Modifier
                    .width(334.dp)
                    .notifyInput(onInteractWithHistory)
                    .imePadding(),
                state = eventHistoryLazyListState,
            )

            // Crossfade between different events
            Crossfade(targetState = hasEventsUiState.eventFeed.selectedEvent) { focusedEvent ->
                val detailLazyListState by derivedStateOf {
                    eventDetailLazyListStates.getValue(focusedEvent.id)
                }

                key(focusedEvent.id) {
                    LazyColumn(
                        state = detailLazyListState,
                        contentPadding = contentPadding,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxSize()
                            .notifyInput {
                                onInteractWithEventDetails(focusedEvent.id)
                            }
                            .imePadding()
                    ) {
                        stickyHeader {
                            val context = LocalContext.current
                            EventDetailTopBar(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(Alignment.End)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EventDetailTopBar(modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(Dp.Hairline, MaterialTheme.colors.onSurface.copy(alpha = .6f)),
        modifier = modifier.padding(end = 16.dp)
    ) {
        Row(Modifier.padding(horizontal = 8.dp)) {
            // Maybe Flag and Delete?
        }
    }
}

/**
 * A [Modifier] that tracks all input, and calls [block] every time input is received.
 */
private fun Modifier.notifyInput(block: () -> Unit): Modifier =
    composed {
        val blockState = rememberUpdatedState(block)
        pointerInput(Unit) {
            while (currentCoroutineContext().isActive) {
                awaitPointerEventScope {
                    awaitPointerEvent(PointerEventPass.Initial)
                    blockState.value()
                }
            }
        }
    }

@Composable
fun EventHistoryScreenWithList(
    uiState: EventHistoryUiState,
    showTopAppBar: Boolean,
    onInteractWithHistory: () -> Unit,
    onInteractWithEventDetails: (String) -> Unit,
    eventListLazyListState: LazyListState,
    openDrawer: () -> Unit,
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier,
    hasEventsContent: @Composable (uiState: EventHistoryUiState.HasEvents, modifier: Modifier) -> Unit,
) {

}

@Composable
fun EventHistoryScreen(
    uiState: EventHistoryUiState,
    showTopAppBar: Boolean,
    onInteractWithHistory: () -> Unit,
    onInteractWithEventDetails: (String) -> Unit,
    openDrawer: () -> Unit,
    scaffoldState: ScaffoldState,
    eventHistoryLazyListState: LazyListState,
    modifier: Modifier = Modifier
) {
    EventHistoryScreenWithList(
        uiState = uiState,
        showTopAppBar = showTopAppBar,
        eventListLazyListState = eventHistoryLazyListState,
        onInteractWithHistory = onInteractWithHistory,
        onInteractWithEventDetails = onInteractWithEventDetails,
        openDrawer = openDrawer,
        scaffoldState = scaffoldState,
        modifier = modifier
    ) { hasEventsContent, contentModifier ->
        EventHistoryList(
            eventHistory = hasEventsContent.eventFeed.allEvents,
            onSelectEvent = onInteractWithEventDetails,
            contentPadding = rememberContentPaddingForScreen(
                additionalTop = if (showTopAppBar) 0.dp else 8.dp
            ),
            modifier = contentModifier,
            state = eventHistoryLazyListState,
        )
    }

}

@Composable
fun EventHistoryList(
    eventHistory: List<Event.CompletedEvent>,
    onSelectEvent: (String) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    state: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        state = state
    ) {
        item {
            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(R.string.past_events),
                style = MaterialTheme.typography.subtitle1
            )
        }
        if (eventHistory.isNotEmpty()) {
            item {
                EventListSection(eventHistory = eventHistory, onSelectEvent = onSelectEvent)
            }
        }
    }
}

@Composable
fun EventListSection(
    eventHistory: List<Event.CompletedEvent>,
    onSelectEvent: (String) -> Unit,
) {
    Column {
        eventHistory.forEach { event ->
            EventHistory(
                event,
                onSelectEvent
            )
            EventListDivider()
        }
    }
}

@Composable
fun EventHistory(
    event: Event.CompletedEvent,
    onSelectEvent: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column {
            // Image
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(event.mediaURL)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Inside,
                contentDescription = null,
                modifier = modifier.clip(CircleShape)
            )

            // Time

            // Auth User
        }
    }
}

@Composable
fun EventListDivider() {
    Divider(
        modifier = Modifier.padding(horizontal = 14.dp),
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.08f)
    )
}

@Composable
fun SingularEvent(
    event: Event.CompletedEvent,
    modifier: Modifier = Modifier,
    onSelectEvent: (String) -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.clickable(onClick = { onSelectEvent(event.id) }),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = event.authUser.displayName,
                style = MaterialTheme.typography.h6,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
            Text(
                text = event.accessTime.toString(),
                style = MaterialTheme.typography.subtitle2,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview("Event History Screen with Event Details")
@Composable
fun PreviewEventHistoryScreenWithEventDetails() {

}

@Preview("Event History Screen")
@Composable
fun PreviewEventHistoryScreen() {

}

@Preview("Singular Event")
@Composable
fun PreviewSingleEvent() {
    val authUser = AccountOwner(
        "123456789",
        "Irene",
        "Adler",
        "Irene",
        "test_acc_owner@gmail.com",
        "https://picsum.photos/200/300"
    )
//    val testEvent = Event.CompletedEvent(
//
//    )
}