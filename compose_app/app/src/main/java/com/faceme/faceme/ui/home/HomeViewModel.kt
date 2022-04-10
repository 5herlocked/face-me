package com.faceme.faceme.ui.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplifyframework.datastore.generated.model.RegisteredUser
import com.faceme.faceme.R
import com.faceme.faceme.model.EventsFeed
import com.faceme.faceme.model.HomeScreenEvent
import com.faceme.faceme.utils.ErrorMessage
import com.faceme.faceme.utils.Result

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*

sealed interface HomeUiState {
    val isLoading: Boolean
    val errorMessages: List<ErrorMessage>

    /**
     * There are no Events to render.
     *
     * This could either be because they are still loading or they failed to load, and we are
     * waiting to reload them.
     */
    data class NoEvents(
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>
    ) : HomeUiState

    /**
     * There are Events to render, as contained in [homeScreenEvent].
     *
     * There is guaranteed to be a [selectedEvent], which is one of the posts from [homeScreenEvent].
     */
    data class HasEvents(
        val homeScreenEvent: HomeScreenEvent,
        val selectedUser: RegisteredUser? = null,
        val isUserOpen: Boolean,
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>
    ) : HomeUiState
}

private data class HomeViewModelState(
    val homeScreenEvent: HomeScreenEvent? = null,
    val selectedUserId: String? = null,
    val isLoading: Boolean = false,
    val isUserOpen: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList(),

    ) {
    fun toUiState(): HomeUiState =
        if (homeScreenEvent == null) {
            HomeUiState.NoEvents(
                isLoading = isLoading,
                errorMessages = errorMessages
            )
        } else {
            HomeUiState.HasEvents(
                homeScreenEvent = homeScreenEvent,
                isLoading = isLoading,
                isUserOpen = isUserOpen,
                selectedUser = homeScreenEvent.detectedUsers.find {
                    it.id == selectedUserId
                }!!,
                errorMessages = errorMessages
            )
        }
}

class HomeViewModel : ViewModel() {
    private val viewModelState = MutableStateFlow(HomeViewModelState(isLoading = true))

    val uiState = viewModelState
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    init {
        refreshEvents()
    }

    fun refreshEvents() {
        viewModelState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val result = getEvents()

            viewModelState.update {
                when(result) {
                    is Result.Success<HomeScreenEvent> -> it.copy(
                        homeScreenEvent = result.data,
                        isLoading = false
                    )
                    is Result.Error -> {
                        val errorMessages = it.errorMessages + ErrorMessage(
                            id = UUID.randomUUID().mostSignificantBits,
                            messageId = R.string.load_error
                        )
                        it.copy(errorMessages = errorMessages, isLoading = false)
                    }
                }
            }
        }
    }

    private fun getEvents(): Result<HomeScreenEvent> {
        TODO("Implement cloud call")
    }

    /*
     * Approve entrance of a user
     */
    fun approveUser(userId: String) {
        viewModelScope.launch {
            // pass it to the method in Event
            
        }
    }

    /*
     * Deny the entrance of a user
     */
    fun rejectUser(userId: String) {
        viewModelScope.launch {
            // pass it to the method in Event

        }
    }

    /**
     * Notify that the user interacted with the feed
     */
    fun interactedWithFeed() {
        viewModelState.update {
            it.copy(isUserOpen = false)
        }
    }

    fun selectUser(userId: String) {
        viewModelState.update {
            it.copy(isUserOpen = true)
        }
    }

    /**
     * Notify that an error was displayed on the screen
     */
    fun errorShown(errorId: Long) {
        viewModelState.update { currentUiState ->
            val errorMessages = currentUiState.errorMessages.filterNot { it.id == errorId }
            currentUiState.copy(errorMessages = errorMessages)
        }
    }

    fun interactWithEventDetails(userId: String) {
        viewModelState.update {
            it.copy(
                selectedUserId = userId,
                isUserOpen = true
            )
        }
    }
}
