package com.faceme.faceme.model

import com.amplifyframework.core.model.temporal.Temporal
import com.amplifyframework.datastore.generated.model.AccountOwner
import com.amplifyframework.datastore.generated.model.RegisteredUser

sealed interface Event {
    val mediaURL: String
    val id: String

    data class IncompleteEvent(
        override val id: String,
        override val mediaURL: String,
        val detectedUsers: List<RegisteredUser>,
    ) : Event

    data class CompletedEvent(
        override val id: String,
        override val mediaURL: String,
        val authUser: User.RegisteredUser,
        val ownerUser: User.AccountOwner,
        // val accessTime: Temporal.DateTime
    ) : Event
}
