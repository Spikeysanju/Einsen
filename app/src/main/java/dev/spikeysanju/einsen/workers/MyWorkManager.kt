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

package dev.spikeysanju.einsen.workers

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import dev.spikeysanju.einsen.model.task.Task
import dev.spikeysanju.einsen.utils.getCalendar
import java.util.concurrent.TimeUnit

fun Context.scheduleReminders(task: Task) {
    val calendar = getCalendar(task.due)
    val initialDelay =
        (calendar.timeInMillis - TimeUnit.HOURS.toMillis(1)) - System.currentTimeMillis()
    if (initialDelay > 0) {
        val data = Data.Builder()
            .putInt(ARG_ID, task.id)
            .build()

        val worker = OneTimeWorkRequest.Builder(ReminderWorker::class.java)
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .setInputData(data)
            .build()
        WorkManager.getInstance(this)
            .enqueueUniqueWork(task.getWorkerId(), ExistingWorkPolicy.REPLACE, worker)
    } else {
        cancelReminder(task)
    }
}

fun Context.cancelReminder(task: Task) {
    WorkManager.getInstance(this).cancelUniqueWork(task.getWorkerId())
}
