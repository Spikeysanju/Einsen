package einsen.spikeysanju.dev.plugins

import einsen.spikeysanju.dev.data.repository.auth.AuthRepository
import einsen.spikeysanju.dev.data.repository.task.TaskRepository
import einsen.spikeysanju.dev.routes.auth.registerAuthenticationRoutes
import einsen.spikeysanju.dev.routes.task.createTask
import einsen.spikeysanju.dev.routes.task.deleteTask
import einsen.spikeysanju.dev.routes.task.getAllTasksOfUser
import einsen.spikeysanju.dev.routes.task.updateTask
import einsen.spikeysanju.dev.service.auth.AuthService
import einsen.spikeysanju.dev.service.task.TaskService
import io.ktor.application.Application
import io.ktor.routing.routing
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val authRepository by inject<AuthRepository>()
    val taskRepository by inject<TaskRepository>()

    val authService = AuthService(
        authRepository = authRepository,
        jwtDomain = environment.config.property("jwt.domain").getString(),
        jwtAudience = environment.config.property("jwt.audience").getString(),
        jwtSecret = environment.config.property("jwt.secret").getString()
    )

    val taskService = TaskService(taskRepository)

    routing {
        registerAuthenticationRoutes(authService)
        // Tasks
        getAllTasksOfUser(taskService)
        createTask(taskService)
        updateTask(taskService)
        deleteTask(taskService)
    }
}
