package com.faceme.faceme.model

data class EventsFeed (
    val selectedEvent: Event.CompletedEvent,
    val allEvents: List<Event.CompletedEvent>
) {

}
