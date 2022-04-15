package com.faceme.faceme.ui.event_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.faceme.faceme.R
import com.faceme.faceme.data.events.EventsRepository
import com.faceme.faceme.model.Event
import com.faceme.faceme.model.EventsFeed
import com.faceme.faceme.utils.ErrorMessage
import com.faceme.faceme.utils.Result
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

sealed interface EventHistoryUiState {
    val isLoading: Boolean
    val errorMessages: List<ErrorMessage>

    // No events to render
    // either loading or failed to load, waiting to reload
    data class NoEvents(
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>
    ) : EventHistoryUiState

    // there are events to be rendered
    // There is an implicit guarantee that [selectedEvent], is one of the events from [eventsFeed]
    data class HasEvents(
        val eventFeed: EventsFeed,
        val selectedEvent: Event? = null,
        val isEventOpen: Boolean,
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>
    ) : EventHistoryUiState
}

private data class EventHistoryViewModelState (
    val eventFeed: EventsFeed? = null,
    val selectedEventId: String? = null,
    val isLoading: Boolean = false,
    val isEventOpen: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList()
) {
    fun toUiState(): EventHistoryUiState =
        if (eventFeed == null) {
            EventHistoryUiState.NoEvents(
                isLoading = isLoading,
                errorMessages = errorMessages
            )
        } else {
            EventHistoryUiState.HasEvents(
                eventFeed,
                selectedEvent = eventFeed.allEvents.find {
                    it.id == selectedEventId
                },
                isEventOpen,
                isLoading,
                errorMessages
            )
        }
}

class EventHistoryViewModel (
    private val eventsRepository: EventsRepository
) : ViewModel() {
    private val viewModelState = MutableStateFlow(EventHistoryViewModelState( isLoading = true))

    val uiState = viewModelState.map { it.toUiState() }
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
            val result = eventsRepository.getEventsFeed()
            viewModelState.update {
                when (result) {
                    is Result.Success -> it.copy(
                        eventFeed = EventsFeed(
                            allEvents = result.data as List<Event.CompletedEvent>,
                            selectedEvent = null
                        ),
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

    private fun getEvents(): Result<EventsFeed> {
        TODO("Not yet implement")
    }

    fun interactWithHistory() {
        viewModelState.update {
            it.copy(isEventOpen = false)
        }
    }

    fun selectEvent(eventId: String) {
        viewModelState.update {
            it.copy(isEventOpen = true)
        }
    }

    fun interactWithEventDetails(eventId: String) {
        viewModelState.update {
            it.copy(
                selectedEventId = eventId,
                isEventOpen = true
            )
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

    /**
     * Factory for HomeViewModel that takes PostsRepository as a dependency
     */
    companion object {
        fun provideFactory(
            eventsRepository: EventsRepository,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return EventHistoryViewModel(eventsRepository) as T
            }
        }
    }
}