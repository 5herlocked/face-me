package com.faceme.faceme.data.events.impl

import com.amplifyframework.datastore.generated.model.AccountOwner
import com.amplifyframework.datastore.generated.model.RegisteredUser
import com.faceme.faceme.data.events.EventsRepository
import com.faceme.faceme.model.Event
import com.faceme.faceme.model.User
import com.faceme.faceme.utils.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
class FakeEventsRepository : EventsRepository {

    private val homeScreenEvent by lazy {
        val accOwner = AccountOwner(
            "000000000",
            "Elaine",
            "Hudson",
            "Mrs. Hudson",
            "test_acc_owner@gmail.com",
            "https://picsum.photos/200/300"
        )
        val detectedUsers = listOf(
            RegisteredUser(
                "123456789",
                "Sherlock",
                "https://picsum.photos/200/300",
                accOwner
            ),
            RegisteredUser(
                "234567891",
                "Wattson",
                "https://picsum.photos/200/300",
                accOwner
            ),
            RegisteredUser(
                "345678912",
                "Irene",
                "https://picsum.photos/200/300",
                accOwner
            )
        )
        val media = "https://picsum.photos/400/600"
        Event.IncompleteEvent(
            id = "123456789",
            mediaURL = media,
            detectedUsers = detectedUsers
        )
    }

    private val eventHistory by lazy {
        val accOwner = User.AccountOwner(
            "000000000",
            "test_acc_owner@gmail.com",
            "Elaine",
            "Mrs. Hudson",
            "https://picsum.photos/200/300"
        )
        val detectedUsers = listOf(
            User.RegisteredUser(
                "123456789",
                "Sherlock",
                "Sherlock",
                "https://picsum.photos/200/300",
                accOwner
            ),
            User.RegisteredUser(
                "234567891",
                "Wattson",
                "Wattson",
                "https://picsum.photos/200/300",
                accOwner
            ),
            User.RegisteredUser(
                "345678912",
                "Irene",
                "Irene",
                "https://picsum.photos/200/300",
                accOwner
            ),
            User.RegisteredUser(
                "456789123",
                "Greg",
                "Greg",
                "https://picsum.photos/200/3003",
                accountOwner = accOwner
            ),
            User.RegisteredUser(
                "567891234",
                "Bob",
                "Bob",
                "https://picsum.photos/200/3003",
                accountOwner = accOwner
            ),
            User.RegisteredUser(
                "678912345",
                "Patrick",
                "Patrick",
                "https://picsum.photos/200/3003",
                accountOwner = accOwner
            )
        )
        listOf(
            Event.CompletedEvent(
                "123456789",
                "https://picsum.photos/200/300",
                detectedUsers[0],
                accOwner
            ),
            Event.CompletedEvent(
                "234567891",
                "https://picsum.photos/200/300",
                detectedUsers[1],
                accOwner
            ),
            Event.CompletedEvent(
                "345678912",
                "https://picsum.photos/200/300",
                detectedUsers[2],
                accOwner
            ),
            Event.CompletedEvent(
                "456789123",
                "https://picsum.photos/200/300",
                detectedUsers[3],
                accOwner
            )
        )
    }

    override suspend fun getHomeScreenEvent(): Result<Event> {
        return Result.Success(homeScreenEvent)
    }

    override suspend fun getEvent(eventID: String?): Result<Event?> {
        return Result.Success(eventHistory.find { event -> event.id == eventID})
    }

    override suspend fun getEventsFeed(): Result<List<Event>> {
        return Result.Success(eventHistory)
    }

}