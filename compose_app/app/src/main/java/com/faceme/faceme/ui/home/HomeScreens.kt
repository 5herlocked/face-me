package com.faceme.faceme.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeFeedWithUserDetailsScreen(
    uiState: HomeUiState,
    showTopAppBar: Boolean,
    onApprove: (String) -> Unit,
    onReject: (String) -> Unit,
    onSelectUser: (String) -> Unit,
    onRefreshEvents: () -> Unit,
    onErrorDismiss: (Long) -> Unit,
    onInteractWithFeed: () -> Unit,
    onInteractWithDetail: (String) -> Unit,
    openDrawer: () -> Unit,
    homeListLazyListState: LazyListState,
    eventDetailLazyListState: Map<String, LazyListState>,
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier,
) {

}

@Composable
fun HomeFeedScreen(
    uiState: HomeUiState,
    showTopAppBar: Boolean,
    onApprove: (String) -> Unit,
    onReject: (String) -> Unit,
    onSelectUser: (String) -> Unit,
    onRefreshEvents: () -> Unit,
    onErrorDismiss: (Long) -> Unit,
    openDrawer: () -> Unit,
    homeListLazyListState: LazyListState,
    eventDetailLazyListState: Map<String, LazyListState>,
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier,
) {

}