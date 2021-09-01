package dev.spikeysanju.einsen.utils

import dev.spikeysanju.einsen.model.task.Priority

fun makeValueRound(value: Float): Float {
    return when (value) {
        0.0F -> 0.0F
        0.8F -> 1.0F
        1.6F -> 2.0F
        2.4F -> 3.0F
        3.2F -> 4.0F
        4.0F -> 5.0F
        else -> 0.0F
    }
}

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
