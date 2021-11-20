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

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

const val DUE_DATE_FORMAT = "dd MMM yyyy hh:mm aa"

fun formatCalendar(calendar: Calendar, dateTimeFormat: String? = DUE_DATE_FORMAT): String {
    val simpleDateFormat = SimpleDateFormat(dateTimeFormat, Locale.getDefault())
    return simpleDateFormat.format(calendar.time)
}

fun getCalendar(dateTime: String): Calendar {
    val simpleDateFormat = SimpleDateFormat(DUE_DATE_FORMAT, Locale.getDefault())
    val cal = Calendar.getInstance()
    try {
        simpleDateFormat.parse(dateTime)?.let {
            cal.time = it
        }
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return cal
}
