/*
 *
 *  * Copyright 2021 Spikey Sanju
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     https://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 *
 */

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
