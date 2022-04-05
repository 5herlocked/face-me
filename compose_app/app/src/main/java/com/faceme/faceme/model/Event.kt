package com.faceme.faceme.model

import com.amplifyframework.core.model.temporal.Temporal

data class Event(
    val id: Int,
    val authUser: EventUser,
    val primaryUser: PrimaryUser,
    val accessTime: Temporal.DateTime
)
