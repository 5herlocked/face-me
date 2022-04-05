package com.faceme.faceme.ui.home


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.faceme.faceme.R
import com.faceme.faceme.model.Event
import com.faceme.faceme.model.EventsFeed
import com.faceme.faceme.utils.ErrorMessage
import com.faceme.faceme.utils.Result

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
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
     * There are Events to render, as contained in [eventsFeed].
     *
     * There is guaranteed to be a [selectedEvent], which is one of the posts from [eventsFeed].
     */
    data class HasEvents(
        val eventsFeed: EventsFeed,
        override val isLoading: Boolean,
        override val errorMessages: List<ErrorMessage>
    ) : HomeUiState
}

private data class HomeViewModelState(
    val eventsFeed: EventsFeed? = null,
    val isLoading: Boolean = false,
    val errorMessages: List<ErrorMessage> = emptyList(),

) {
    fun toUiState(): HomeUiState =
        if (eventsFeed == null) {
            HomeUiState.NoEvents(
                isLoading = isLoading,
                errorMessages = errorMessages
            )
        } else {
            HomeUiState.HasEvents(
                eventsFeed = eventsFeed,
                isLoading = isLoading,
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

    private fun refreshEvents() {
        viewModelState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val result = getEvents() // TODO: Replace this with a cloud call

            viewModelState.update {
                when(result) {
                    is Result.Success<EventsFeed> -> it.copy(eventsFeed = result.data, isLoading = false)
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
        TODO("NOT IMPLEMENTED")
    }
}
