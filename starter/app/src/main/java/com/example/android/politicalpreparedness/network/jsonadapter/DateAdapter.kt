package com.example.android.politicalpreparedness.network.jsonadapter

import android.annotation.SuppressLint
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.SimpleDateFormat
import java.util.*

class DateAdapter {
    companion object {
        private const val DATE_FORMAT = "yyyy-MM-dd"
    }

    @SuppressLint("SimpleDateFormat")
    @FromJson
    fun divisionFromJson(electionDay: String): Date {
        return SimpleDateFormat(DATE_FORMAT).parse(electionDay)
    }

    @ToJson
    fun divisionToJson(date: Date): String {
        val dateFormatter = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return dateFormatter.format(date)
    }
}