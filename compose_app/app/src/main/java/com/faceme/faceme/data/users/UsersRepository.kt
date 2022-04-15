package com.faceme.faceme.data.users

import com.faceme.faceme.model.User
import com.faceme.faceme.utils.Result

interface UsersRepository {
    suspend fun getUser(userId: String?): Result<User?>
    suspend fun getUserList(): List<User>
    suspend fun getRandomUsers(): List<User>
}