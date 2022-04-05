package com.faceme.faceme.model

import androidx.annotation.DrawableRes
import java.net.URL

data class EventUser(
    val id: String,
    val firstName: String,
    val lastName: String? = null,
    val displayName: String = firstName,
    val imageUrl: URL? = null,
    val primaryUser: PrimaryUser,
    @DrawableRes val imageId: Int,
    @DrawableRes val imageThumbId: Int
)