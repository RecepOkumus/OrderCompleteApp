package com.rcpokumus.ordercompleteapp.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object TimeUtil {

    fun formatTimestampToDate(timestamp: Long): String {
        val date = Date(timestamp)
        val format = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        return format.format(date)
    }

    fun isTimestampBeforeNow(timestamp: Long): Boolean {
        val currentTime = System.currentTimeMillis()
        return timestamp < currentTime
    }
}