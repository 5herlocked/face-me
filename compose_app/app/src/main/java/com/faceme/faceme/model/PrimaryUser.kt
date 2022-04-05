package com.faceme.faceme.model

import android.provider.ContactsContract
import androidx.annotation.DrawableRes
import java.net.URL

data class PrimaryUser(
    val id: String,
    val firstName: String,
    val lastName: String? = null,
    val displayName: String? = firstName,
    val email: ContactsContract.CommonDataKinds.Email,
    val imageUrl: URL? = null,
    @DrawableRes val imageId: Int,
    @DrawableRes val imageThumbId: Int
)

