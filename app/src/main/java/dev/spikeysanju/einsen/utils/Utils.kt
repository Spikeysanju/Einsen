package dev.spikeysanju.einsen.utils

import dev.spikeysanju.einsen.model.task.Priority

/**
 * This function helps to perform calculation for the Einsenhower matrix technique.
 * The Eisenhower Decision Matrix, also known as the Urgent-Important Matrix, is a powerful tool
 * for time management. It helps you to decide on and prioritize your tasks based on urgency and
 * importance while sorting out less urgent and less important tasks.
 *
 * Points Table
 * Urgent -> 4 pts
 * Important -> 3 pts
 * Delegate -> 2 pts
 * Dump -> 1 pts
 *
 * @param priorityAverage
 * @return Priority
 */
fun calculatePriority(priorityAverage: Float): Priority {

    return when {
        priorityAverage >= 4 -> {
            Priority.URGENT
        }
        priorityAverage >= 3 -> {
            Priority.IMPORTANT
        }
        priorityAverage >= 2 -> {
            Priority.DELEGATE
        }
        priorityAverage >= 1 -> {
            Priority.DUMP
        }
        else -> {
            Priority.URGENT
        }
    }
}
