package einsen.spikeysanju.dev.di

import einsen.spikeysanju.dev.data.repository.task.TaskRepository
import einsen.spikeysanju.dev.data.repository.task.TaskRepositoryImpl
import einsen.spikeysanju.dev.data.repository.user.UserRepository
import einsen.spikeysanju.dev.data.repository.user.UserRepositoryImpl
import org.koin.dsl.module

val mainModule = module(createdAtStart = true) {
    single<UserRepository> {
        UserRepositoryImpl()
    }
    single<TaskRepository> {
        TaskRepositoryImpl()
    }
}
