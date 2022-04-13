package com.faceme.faceme.ui.event_history

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun EventHistoryWithEventDetailsScreen(
    uiState: EventHistoryUiState,
    showTopAppBar: Boolean,
    onInteractWithHistory: () -> Unit,
    onInteractWithEventDetails: (String) -> Unit,
    openDrawer: () -> Unit,
    scaffoldState: ScaffoldState,
    eventHistoryLazyListState: LazyListState,
    eventDetailLazyListStates: Map<String, LazyListState>
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
    eventHistoryLazyListState: LazyListState
) {

}

@Preview("Event History Screen with Event Details")
@Composable
fun PreviewEventHistoryScreenWithEventDetails() {

}

@Preview("Event History Screen")
@Composable
fun PreviewEventHistoryScreen() {

}