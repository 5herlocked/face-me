package com.faceme.faceme.model

import com.amplifyframework.core.model.temporal.Temporal
import com.amplifyframework.datastore.generated.model.AccountOwner
import com.amplifyframework.datastore.generated.model.RegisteredUser

sealed interface Event {
    val id: String

    data class IncompleteEvent(
        override val id: String,
        val detectedUsers: List<RegisteredUser>,
    ) : Event

    data class CompletedEvent(
        override val id: String,
        val authUser: RegisteredUser,
        val ownerUser: AccountOwner,
        val mediaURL: String,
        val accessTime: Temporal.DateTime
    ) : Event
}
