package com.example.android.politicalpreparedness.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtil {

    @SuppressLint("SimpleDateFormat")
    fun convertToEDTFormat(electionDate: Date): String {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("EDT"))
        val time = TimeZone.getTimeZone("America/New_York")
        val simpleDateFormat = SimpleDateFormat("EEE MMMM dd hh:mm:ss 'EDT' yyyy")
        calendar.timeZone = time
        return simpleDateFormat.format(electionDate)
    }
}