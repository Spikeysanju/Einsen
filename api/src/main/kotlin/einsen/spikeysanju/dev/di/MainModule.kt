package einsen.spikeysanju.dev.di

import einsen.spikeysanju.dev.data.repository.task.TaskRepository
import einsen.spikeysanju.dev.data.repository.task.TaskRepositoryImpl
import einsen.spikeysanju.dev.data.repository.auth.AuthRepository
import einsen.spikeysanju.dev.data.repository.auth.AuthRepositoryImpl
import org.koin.dsl.module

val mainModule = module(createdAtStart = true) {
    single<AuthRepository> {
        AuthRepositoryImpl()
    }
    single<TaskRepository> {
        TaskRepositoryImpl()
    }
}
