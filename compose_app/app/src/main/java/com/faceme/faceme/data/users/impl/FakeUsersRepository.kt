package com.faceme.faceme.data.users.impl

import com.faceme.faceme.data.users.UsersRepository
import com.faceme.faceme.model.User
import com.faceme.faceme.utils.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class)
class FakeUsersRepository : UsersRepository{

    private val accountOwner by lazy {
        User.AccountOwner(
            id = "000000000",
            firstName = "Elaine",
            displayName = "Mrs. Hudson",
            email = "test_acc_owner@gmail.com",
            verifiedImage = "https://picsum.photos/200/300"
        )
    }

    private val users by lazy {
        listOf(
            User.RegisteredUser(
                "123456789",
                "Sherlock",
                "Sherlock",
                "https://picsum.photos/200/300",
                accountOwner = accountOwner
            ),
            User.RegisteredUser(
                id ="234567891",
                firstName = "Wattson",
                "Wattson",
                verifiedImage = "https://picsum.photos/200/300",
                accountOwner = accountOwner
            ),
            User.RegisteredUser(
                "345678912",
                "Irene",
                "Irene",
                "https://picsum.photos/200/300",
                accountOwner = accountOwner
            ),
            User.RegisteredUser(
                "456789123",
                "Greg",
                "Greg",
                "https://picsum.photos/200/3003",
                accountOwner = accountOwner
            ),
            User.RegisteredUser(
                "567891234",
                "Bob",
                "Bob",
                "https://picsum.photos/200/3003",
                accountOwner = accountOwner
            ),
            User.RegisteredUser(
                "678912345",
                "Patrick",
                "Patrick",
                "https://picsum.photos/200/3003",
                accountOwner = accountOwner
            )
        )
    }

    override suspend fun getRandomUsers(): List<User> {
        val randStart = Random.nextInt(0, users.size)
        val randEnd = Random.nextInt(randStart, users.size)
        return users.subList(
            randStart, randEnd
        )
    }

    override suspend fun getUser(userId: String?): Result<User?> {
        if (userId == accountOwner.id) {
            return Result.Success(accountOwner)
        }
        return Result.Success(users.find { user -> user.id == userId })
    }

    override suspend fun getUserList(): List<User> {
        return users
    }

}