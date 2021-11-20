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

package dev.spikeysanju.einsen.model.task

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class Task(
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "description")
    val description: String = "",
    @ColumnInfo(name = "category")
    val category: String = "",
    @ColumnInfo(name = "Emoji")
    val emoji: String = "",
    @ColumnInfo(name = "urgency")
    val urgency: Int = 0,
    @ColumnInfo(name = "importance")
    val importance: Int = 0,
    @ColumnInfo(name = "priority")
    val priority: Priority = Priority.IMPORTANT,
    @ColumnInfo(name = "timer")
    val due: String = "",
    @ColumnInfo(name = "isCompleted")
    val isCompleted: Boolean,
    @ColumnInfo(name = "createdAt")
    val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "updatedAt")
    val updatedAt: Long = System.currentTimeMillis(),
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0
) {
    fun getWorkerId() = "reminder_$id"
}

enum class Priority(count: Int) {
    URGENT(4),
    IMPORTANT(3),
    DELEGATE(2),
    DUMP(1)
}

fun task(block: TaskBuilder.() -> Unit): Task = TaskBuilder().apply(block).build()
