package com.faceme.faceme.model

sealed interface User{
    val id: String
    val firstName: String
    val displayName: String
    val verifiedImage: String

    data class AccountOwner(
        override val id: String,
        val email: String,
        override val firstName: String,
        override val displayName: String,
        override val verifiedImage: String
    ): User

    data class RegisteredUser(
        override val id: String,
        override val firstName: String,
        override val displayName: String,
        override val verifiedImage: String,
        val accountOwner: AccountOwner
    ): User
}