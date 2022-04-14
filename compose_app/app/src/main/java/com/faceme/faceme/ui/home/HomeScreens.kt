package com.faceme.faceme.ui.home

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.amplifyframework.datastore.generated.model.AccountOwner
import com.amplifyframework.datastore.generated.model.RegisteredUser
import com.faceme.faceme.R
import com.faceme.faceme.model.Event
import com.faceme.faceme.ui.components.FaceMeSnackbarHost
import com.faceme.faceme.ui.rememberContentPaddingForScreen
import com.faceme.faceme.ui.theme.FaceMeTheme
import com.faceme.faceme.ui.user_details.userDetails
import com.faceme.faceme.ui.utils.ApproveButton
import com.faceme.faceme.ui.utils.RejectButton
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.isActive

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
    userDetailLazyListStates: Map<String, LazyListState>,
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier,
) {
    HomeScreenWithList(
        uiState = uiState,
        showTopAppBar = showTopAppBar,
        onRefreshEvents = onRefreshEvents,
        onErrorDismiss = onErrorDismiss,
        openDrawer = openDrawer,
        homeListLazyListState = homeListLazyListState,
        scaffoldState = scaffoldState
    ) { hasEventsUiState, contentModifier ->
        val contentPadding = rememberContentPaddingForScreen(additionalTop = 8.dp)
        Row(contentModifier) {
            EventUserList(
                homeScreenEvent = hasEventsUiState.homeScreenEvent,
                onSelectUser = onSelectUser,
                onApprove = onApprove,
                onReject = onReject,
                modifier = Modifier
                    .width(334.dp)
                    .notifyInput(onInteractWithFeed)
                    .imePadding(), // add padding for the on-screen keyboard
                state = homeListLazyListState,
            )

            // Crossfade between different users
            Crossfade(targetState = hasEventsUiState.selectedUser) { focusedUser ->
                val detailLazyListState by derivedStateOf {
                    userDetailLazyListStates.getValue(focusedUser!!.id)
                }

                key(focusedUser!!.id) {
                    LazyColumn(
                        state = detailLazyListState,
                        contentPadding = contentPadding,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxSize()
                            .notifyInput {
                                onInteractWithDetail(focusedUser.id)
                            }
                            .imePadding()
                    ) {
                        stickyHeader {
                            val context = LocalContext.current
                            UserDetailTopBar(
                                onApprove = { onApprove(focusedUser.id) },
                                onReject = { onReject(focusedUser.id) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(Alignment.End)
                            )

                            this@LazyColumn.userDetails(focusedUser)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UserDetailTopBar(
    onApprove: () -> Unit,
    onReject: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(Dp.Hairline, MaterialTheme.colors.onSurface.copy(alpha = .6f)),
        modifier = modifier.padding(end = 16.dp)
    ) {
        Row(Modifier.padding(horizontal = 8.dp)) {
            ApproveButton(onClick = onApprove)
            RejectButton(onClick = onReject)
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
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier,
) {
    HomeScreenWithList(
        uiState = uiState,
        showTopAppBar = showTopAppBar,
        onRefreshEvents = onRefreshEvents,
        onErrorDismiss = onErrorDismiss,
        openDrawer = openDrawer,
        homeListLazyListState = homeListLazyListState,
        scaffoldState = scaffoldState,
        modifier = modifier
    ) { hasEventsUiState, contentModifier ->
        EventUserList(
            homeScreenEvent = hasEventsUiState.homeScreenEvent,
            onSelectUser = onSelectUser,
            onApprove = onApprove,
            onReject = onReject,
            contentPadding = rememberContentPaddingForScreen(
                additionalTop = if (showTopAppBar) 0.dp else 8.dp
            ),
            modifier = contentModifier,
            state = homeListLazyListState
        )
    }
}

@Composable
fun EventUserList(
    homeScreenEvent: Event.IncompleteEvent,
    onSelectUser: (String) -> Unit,
    onApprove: (String) -> Unit,
    onReject: (String) -> Unit,
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
            // camera feed or image here
        }

        item {
            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(R.string.detected_users),
                style = MaterialTheme.typography.subtitle1
            )
        }
        if (homeScreenEvent.detectedUsers.isNotEmpty()) {
            item {
                UsersListSection(
                    homeScreenEvent.detectedUsers,
                    onApprove,
                    onReject,
                    onSelectUser
                )
            }
        }
    }
}

@Composable
fun UsersListSection(
    detectedUsers: List<RegisteredUser>,
    onApprove: (String) -> Unit,
    onReject: (String) -> Unit,
    onSelectUser: (String) -> Unit
) {
    Column {
        detectedUsers.forEach { user ->
            DetectedUser(
                user,
                onApprove,
                onReject,
                onSelectUser,
            )
            UserListDivider()
        }
    }
}

@Composable
fun UserListDivider() {
    Divider(
        modifier = Modifier.padding(horizontal = 14.dp),
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.08f)
    )
}

@Composable
fun DetectedUser(
    user: RegisteredUser,
    onApprove: (String) -> Unit,
    onReject: (String) -> Unit,
    onSelectUser: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .clickable(onClick = { onSelectUser(user.id) })
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = user.displayName,
                style = MaterialTheme.typography.h6,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { onApprove(user.id) },
                    modifier = Modifier.padding(bottom = 8.dp, start = 8.dp, end = 8.dp),
                ) {
                    // Accept button
                    Icon(
                        Icons.Filled.Check,
                        contentDescription = stringResource(R.string.approve),
                        modifier = modifier.size(ButtonDefaults.IconSize),
                    )
                    Spacer(modifier.size(ButtonDefaults.IconSpacing))
                    Text(stringResource(id = R.string.approve))
                }
                Button(
                    onClick = { onReject(user.id) },
                    modifier = Modifier.padding(bottom = 8.dp, start = 8.dp, end = 8.dp)
                ) {
                    // Reject button
                    Icon(
                        Icons.Filled.Block,
                        contentDescription = stringResource(id = R.string.reject),
                        modifier = modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(modifier.size(ButtonDefaults.IconSpacing))
                    Text(stringResource(id = R.string.reject))
                }
            }
        }
    }
}

@Composable
private fun HomeScreenWithList(
    uiState: HomeUiState,
    showTopAppBar: Boolean,
    onRefreshEvents: () -> Unit,
    onErrorDismiss: (Long) -> Unit,
    openDrawer: () -> Unit,
    homeListLazyListState: LazyListState,
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier,
    hasEventsContent: @Composable (
        uiState: HomeUiState.HasEvents,
        modifier: Modifier
    ) -> Unit
) {
    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = { FaceMeSnackbarHost(hostState = it) },
        topBar = {
            if (showTopAppBar) {
                HomeTopAppbar(
                    openDrawer = openDrawer,
                    elevation = if (!homeListLazyListState.isScrollInProgress) 0.dp else 4.dp
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        val contentModifier = Modifier.padding(innerPadding)

        LoadingContent(
            empty = when (uiState) {
                is HomeUiState.HasEvents -> false
                is HomeUiState.NoEvents -> uiState.isLoading
            },
            emptyContent = { FullScreenLoading() },
            loading = uiState.isLoading,
            onRefresh = onRefreshEvents,
            content = {
                when (uiState) {
                    is HomeUiState.HasEvents -> hasEventsContent(uiState, contentModifier)
                    is HomeUiState.NoEvents -> {
                        if (uiState.errorMessages.isEmpty()) {
                            // no events, no errors, let the user refresh manually

                            TextButton(
                                onClick = onRefreshEvents,
                                modifier = modifier.fillMaxSize()
                            ) {
                                Text(
                                    stringResource(id = R.string.home_tap_to_load_content),
                                    textAlign = TextAlign.Center
                                )
                            }
                        } else {
                            Box(contentModifier.fillMaxSize()) {
                                // currently in error, don't show anything
                            }
                        }
                    }
                }
            }
        )
    }

    // Process one error message at a time and show them as Snackbars in the UI
    if (uiState.errorMessages.isNotEmpty()) {
        // Remember the errorMessage to display on the screen
        val errorMessage = remember(uiState) { uiState.errorMessages[0] }

        // Get the text to show on the message from resources
        val errorMessageText: String = stringResource(errorMessage.messageId)
        val retryMessageText = stringResource(id = R.string.retry)

        // If onRefreshPosts or onErrorDismiss change while the LaunchedEffect is running,
        // don't restart the effect and use the latest lambda values.
        val onRefreshPostsState by rememberUpdatedState(onRefreshEvents)
        val onErrorDismissState by rememberUpdatedState(onErrorDismiss)

        // Effect running in a coroutine that displays the Snackbar on the screen
        // If there's a change to errorMessageText, retryMessageText or scaffoldState,
        // the previous effect will be cancelled and a new one will start with the new values
        LaunchedEffect(errorMessageText, retryMessageText, scaffoldState) {
            val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                message = errorMessageText,
                actionLabel = retryMessageText
            )
            if (snackbarResult == SnackbarResult.ActionPerformed) {
                onRefreshPostsState()
            }
            // Once the message is displayed and dismissed, notify the ViewModel
            onErrorDismissState(errorMessage.id)
        }
    }
}

@Composable
fun FullScreenLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun LoadingContent(
    empty: Boolean,
    emptyContent: @Composable () -> Unit,
    loading: Boolean,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit
) {
    if (empty) {
        emptyContent()
    } else {
        SwipeRefresh(
            state = rememberSwipeRefreshState(loading),
            onRefresh = onRefresh,
            content = content,
        )
    }
}

@Composable
fun HomeTopAppbar(openDrawer: () -> Unit, elevation: Dp) {
    val title = stringResource(id = R.string.app_name)
    TopAppBar(
        title = {
            Icon(
                painter = painterResource(R.drawable.ic_faceme_logo),
                contentDescription = title,
                tint = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 4.dp, top = 10.dp)
            )
        },
        navigationIcon = {
            IconButton(onClick = openDrawer) {
                Icon(
                    imageVector = Icons.Filled.Navigation,
                    contentDescription = stringResource(id = R.string.cd_open_navigation_drawer)
                )
            }
        },
        backgroundColor = MaterialTheme.colors.surface,
        elevation = elevation
    )
}

@Preview()
@Composable
fun PreviewHomeScreen() {
    val accOwner = AccountOwner(
        "000000000",
        "Elaine",
        "Hudson",
        "Mrs. Hudson",
        "test_acc_owner@gmail.com",
        "https://picsum.photos/200/300"
    )
    val detectedUsers = listOf(
        RegisteredUser(
            "123456789",
            "Sherlock",
            "https://picsum.photos/200/300",
            accOwner
        ),
        RegisteredUser(
            "234567891",
            "Wattson",
            "https://picsum.photos/200/300",
            accOwner
        ),
        RegisteredUser(
            "345678912",
            "Irene",
            "https://picsum.photos/200/300",
            accOwner
        )
    )
    val media = "https://picsum.photos/200/300"
    FaceMeTheme {
        HomeFeedScreen(
            uiState = HomeUiState.HasEvents(
                homeScreenEvent = Event.IncompleteEvent(
                    id = "123456789",
                    detectedUsers = detectedUsers
                ),
                selectedUser = null,
                isUserOpen = false,
                isLoading = false,
                errorMessages = emptyList(),
            ),
            showTopAppBar = false,
            onApprove = {},
            onReject = {},
            onSelectUser = {},
            onRefreshEvents = { /*TODO*/ },
            onErrorDismiss = {},
            openDrawer = { /*TODO*/ },
            homeListLazyListState = rememberLazyListState(),
            scaffoldState = rememberScaffoldState()
        )
    }
}
