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

package dev.spikeysanju.einsen.components

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import java.util.*


fun Context?.showDatePicker(
    defaultCalendar: Calendar,
    onDismiss: (() -> Unit)? = null,
    onDateSelect: (Calendar) -> Unit
) {
    (this as? FragmentActivity)?.supportFragmentManager?.let { manager ->
        val builder = MaterialDatePicker.Builder.datePicker()
            .setSelection(defaultCalendar.timeInMillis)
            .build()
        builder.addOnPositiveButtonClickListener { selectedDate ->
            val newCal = Calendar.getInstance()
            newCal.timeInMillis = selectedDate
            onDateSelect.invoke(newCal)
        }
        builder.addOnDismissListener {
            onDismiss?.invoke()
        }
        builder.show(manager, "DatePicker")
    }
}

fun Context?.showTimePicker(
    defaultCalendar: Calendar,
    onDismiss: (() -> Unit)? = null,
    onTimeSelected: (Calendar) -> Unit
) {
    (this as? FragmentActivity)?.supportFragmentManager?.let { manager ->
        val builder = MaterialTimePicker.Builder()
            .setHour(defaultCalendar.get(Calendar.HOUR_OF_DAY))
            .setMinute(defaultCalendar.get(Calendar.MINUTE))
            .build()
        builder.addOnPositiveButtonClickListener {
            defaultCalendar.set(Calendar.HOUR_OF_DAY, builder.hour)
            defaultCalendar.set(Calendar.MINUTE, builder.minute)
            onTimeSelected.invoke(defaultCalendar)
        }
        builder.addOnDismissListener {
            onDismiss?.invoke()
        }
        builder.show(manager, "TimePicker")
    }
}
