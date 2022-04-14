package com.faceme.faceme.ui.event_history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amplifyframework.datastore.generated.model.AccountOwner
import com.faceme.faceme.model.Event

@Composable
fun EventHistoryWithEventDetailsScreen(
    uiState: EventHistoryUiState,
    showTopAppBar: Boolean,
    onInteractWithHistory: () -> Unit,
    onInteractWithEventDetails: (String) -> Unit,
    openDrawer: () -> Unit,
    scaffoldState: ScaffoldState,
    eventHistoryLazyListState: LazyListState,
    eventDetailLazyListStates: Map<String, LazyListState>,
    modifier: Modifier = Modifier
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

}

@Composable
fun EventHistoryWithList(
    uiState: EventHistoryUiState,
    showTopAppBar: Boolean,
    onInteractWithHistory: () -> Unit,
    onInteractWithEventDetails: (String) -> Unit,
    openDrawer: () -> Unit,
    scaffoldState: ScaffoldState,
    eventHistoryLazyListState: LazyListState,
    modifier: Modifier = Modifier
) {

}

@Composable
fun EventListSection(
    eventHistory: List<Event.CompletedEvent>,
    onSelectEvent: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        eventHistory.forEach { event ->
            SingularEvent(
                event = event,
                modifier = modifier,
                onSelectEvent = onSelectEvent
            )
            EventListDivider()
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