package com.faceme.faceme.model

import com.amplifyframework.datastore.generated.model.RegisteredUser

data class HomeScreenEvent(
    val media: String,
    val detectedUsers: List<RegisteredUser>,
)
