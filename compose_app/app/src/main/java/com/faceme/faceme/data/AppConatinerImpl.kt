package com.faceme.faceme.data

import android.content.Context
import com.faceme.faceme.data.events.EventsRepository
import com.faceme.faceme.data.events.impl.FakeEventsRepository
import com.faceme.faceme.data.users.UsersRepository
import com.faceme.faceme.data.users.impl.FakeUsersRepository

interface AppContainer {
    val eventsRepository: EventsRepository
    val usersRepository: UsersRepository
}

class AppContainerImpl(private val applicationContext: Context) : AppContainer {
    override val eventsRepository: EventsRepository by lazy {
        FakeEventsRepository()
    }

    override val usersRepository: UsersRepository by lazy {
        FakeUsersRepository()
    }
}