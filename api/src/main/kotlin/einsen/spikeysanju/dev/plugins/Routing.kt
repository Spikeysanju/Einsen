package einsen.spikeysanju.dev.plugins

import einsen.spikeysanju.dev.data.repository.task.TaskRepository
import einsen.spikeysanju.dev.data.repository.user.UserRepository
import einsen.spikeysanju.dev.routes.task.createTask
import einsen.spikeysanju.dev.routes.task.getAllTasksOfUser
import einsen.spikeysanju.dev.routes.task.updateTask
import einsen.spikeysanju.dev.routes.user.createAccount
import einsen.spikeysanju.dev.routes.user.login
import einsen.spikeysanju.dev.service.task.TaskService
import einsen.spikeysanju.dev.service.user.UserService
import io.ktor.application.Application
import io.ktor.routing.routing
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val userRepository by inject<UserRepository>()
    val taskRepository by inject<TaskRepository>()

    val userService = UserService(
        userRepository = userRepository,
        jwtDomain = environment.config.property("jwt.domain").getString(),
        jwtAudience = environment.config.property("jwt.audience").getString(),
        jwtSecret = environment.config.property("jwt.secret").getString()
    )

    val taskService = TaskService(taskRepository)

    routing {
        createAccount(userService)
        login(userService)
        // Tasks
        getAllTasksOfUser(taskService)
        createTask(taskService)
        updateTask(taskService)
    }
}
