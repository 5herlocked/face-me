package com.faceme.faceme.ui.event_history

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import com.faceme.faceme.model.Event

@Composable
fun EventHistoryRoute(
    eventHistoryViewModel: EventHistoryViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    scaffoldState: ScaffoldState  = rememberScaffoldState(),
) {
    val uiState by eventHistoryViewModel.uiState.collectAsState()

    EventHistoryRoute(
        uiState = uiState,
        isExpandedScreen = isExpandedScreen,
        onInteractWithHistory = { eventHistoryViewModel.interactWithHistory() },
        onInteractWithEventDetails = { eventHistoryViewModel.interactWithEventDetails(it) },
        openDrawer = openDrawer,
        scaffoldState = scaffoldState
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EventHistoryRoute(
    uiState: EventHistoryUiState,
    isExpandedScreen: Boolean,
    onInteractWithHistory: () -> Unit,
    onInteractWithEventDetails: (String) -> Unit,
    openDrawer: () -> Unit,
    scaffoldState: ScaffoldState
) {
    val eventHistoryLazyListState = rememberLazyListState()
    val eventDetailLazyListStates = when (uiState) {
        is EventHistoryUiState.HasEvents -> uiState.eventFeed.allEvents
        is EventHistoryUiState.NoEvents -> emptyList()
    }.associate { event: Event ->
        key(event.id) {
            event.id to rememberLazyListState()
        }
    }

    val eventHistoryScreenType = getEventHistoryScreenType(isExpandedScreen, uiState)

    when(eventHistoryScreenType) {
        EventHistoryScreenType.EventDetails -> {

            EventHistoryWithEventDetailsScreen(
                uiState = uiState,
                showTopAppBar = !isExpandedScreen,
                onInteractWithHistory = onInteractWithHistory,
                onInteractWithEventDetails = onInteractWithEventDetails,
                openDrawer = openDrawer,
                scaffoldState = scaffoldState
            )

            BackHandler {
                onInteractWithHistory()
            }
        }

        EventHistoryScreenType.History -> {
            EventHistoryScreen(

            )
        }
    }

}

/**
 * A precise enumeration of which type of screen to display at the home route.
 *
 * There are 2 options:
 * - [History], which displays just the list of all articles
 * - [EventDetails], which displays just a specific article.
 */
private enum class EventHistoryScreenType {
    History,
    EventDetails
}

@Composable
private fun getEventHistoryScreenType(
    expandedScreen: Boolean,
    uiState: EventHistoryUiState
): EventHistoryScreenType = when(expandedScreen) {
    false -> {
        when(uiState) {
            is EventHistoryUiState.HasEvents -> {
                if (uiState.isEventOpen) {
                    EventHistoryScreenType.EventDetails
                } else {
                    EventHistoryScreenType.History
                }
            }
            is EventHistoryUiState.NoEvents -> EventHistoryScreenType.History
        }
    }
    true -> EventHistoryScreenType.EventDetails
}