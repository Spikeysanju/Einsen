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

import android.os.Parcel
import android.os.Parcelable
import com.google.android.material.datepicker.CalendarConstraints

class DateValidator() : CalendarConstraints.DateValidator {
    var startDate: Long = -1
    var endDate: Long = -1

    constructor(startDate: Long = -1, endDate: Long = -1) : this() {
        this.startDate = startDate
        this.endDate = endDate
    }

    constructor(parcel: Parcel) : this() {
        startDate = parcel.readLong()
        endDate = parcel.readLong()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(startDate)
        parcel.writeLong(endDate)
    }

    override fun isValid(date: Long): Boolean {
        return if (startDate != -1L && endDate != -1L && date in startDate..endDate) {
            // valid date between start and end date
            true
        } else if (startDate == -1L && date < endDate) {
            true
        } else endDate == -1L && date >= startDate
    }


    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DateValidator> {
        override fun createFromParcel(parcel: Parcel): DateValidator {
            return DateValidator(parcel)
        }

        override fun newArray(size: Int): Array<DateValidator?> {
            return arrayOfNulls(size)
        }
    }

}
