package com.faceme.faceme.ui.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import com.faceme.faceme.model.Event

@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel,
    isExpandedScreen: Boolean,
    openDrawer: () -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
) {
    val uiState by homeViewModel.uiState.collectAsState()

    HomeRoute(
        uiState = uiState,
        isExpandedScreen = isExpandedScreen,
        onApprove = { homeViewModel.approveUser(it) },
        onReject = { homeViewModel.rejectUser(it) },
        onSelectUser = { homeViewModel.selectUser(it) },
        onRefreshEvents = {homeViewModel.refreshEvents() },
        onErrorDismiss = { homeViewModel.errorShown(it) },
        onInteractWithFeed = { homeViewModel.interactedWithFeed() },
        onInteractWithEventDetails = { homeViewModel.interactWithEventDetails(it) },
        openDrawer = openDrawer,
        scaffoldState = scaffoldState
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeRoute(
    uiState: HomeUiState,
    isExpandedScreen: Boolean,
    onApprove: (String) -> Unit,
    onReject: (String) -> Unit,
    onSelectUser: (String) -> Unit,
    onRefreshEvents: () -> Unit,
    onErrorDismiss: (Long) -> Unit,
    onInteractWithFeed: () -> Unit,
    onInteractWithEventDetails: (String) -> Unit,
    openDrawer: () -> Unit,
    scaffoldState: ScaffoldState
) {
    val homeListLazyListState = rememberLazyListState()
    val eventDetailLazyListStates = when (uiState) {
        is HomeUiState.HasEvents -> uiState.eventsFeed.allEvents
        is HomeUiState.NoEvents -> emptyList()
    }.associate { event : Event ->
        key(event.id) {
            event.id to rememberLazyListState()
        }
    }

    val homeScreenType = getHomeScreenType(isExpandedScreen, uiState)

    when (homeScreenType) {
        HomeScreenType.UserDetails -> {
            HomeFeedWithUserDetailsScreen(
                uiState = uiState,
                showTopAppBar = !isExpandedScreen,
                onApprove = onApprove,
                onReject = onReject,
                onSelectUser = onSelectUser,
                onRefreshEvents = onRefreshEvents,
                onErrorDismiss = onErrorDismiss,
                onInteractWithFeed = onInteractWithFeed,
                onInteractWithDetail = onInteractWithEventDetails,
                openDrawer = openDrawer,
                homeListLazyListState = homeListLazyListState,
                eventDetailLazyListState = eventDetailLazyListStates,
                scaffoldState = scaffoldState
            )

            BackHandler {
                onInteractWithFeed()
            }
        }

        HomeScreenType.Feed -> {
            HomeFeedScreen(
                uiState = uiState,
                showTopAppBar = !isExpandedScreen,
                onApprove = onApprove,
                onReject = onReject,
                onSelectUser = onSelectUser,
                onRefreshEvents = onRefreshEvents,
                onErrorDismiss = onErrorDismiss,
                openDrawer = openDrawer,
                homeListLazyListState = homeListLazyListState,
                eventDetailLazyListState = eventDetailLazyListStates,
                scaffoldState = scaffoldState
            )
        }
    }
}

/**
 * A precise enumeration of which type of screen to display at the home route.
 *
 * There are 2 options:
 * - [Feed], which displays just the list of all articles
 * - [UserDetails], which displays just a specific article.
 */
private enum class HomeScreenType {
    Feed,
    UserDetails
}
@Composable
private fun getHomeScreenType(
    expandedScreen: Boolean,
    uiState: HomeUiState
): HomeScreenType = when(expandedScreen) {
    false -> {
        when(uiState) {
            is HomeUiState.HasEvents -> {
                if (uiState.isUserOpen) {
                    HomeScreenType.UserDetails
                } else {
                    HomeScreenType.Feed
                }
            }
            is HomeUiState.NoEvents -> HomeScreenType.Feed
        }
    }
    true -> HomeScreenType.UserDetails
}
