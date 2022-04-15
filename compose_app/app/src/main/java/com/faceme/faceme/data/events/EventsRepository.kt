package com.faceme.faceme.data.events

import com.faceme.faceme.model.Event
import com.faceme.faceme.model.EventsFeed
import com.faceme.faceme.utils.Result

interface EventsRepository {
    suspend fun getEvent(eventID: String?): com.faceme.faceme.utils.Result<Event?>

    suspend fun getEventsFeed(): Result<List<Event>>
    suspend fun getHomeScreenEvent(): com.faceme.faceme.utils.Result<Event>
}