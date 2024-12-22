package com.leonid.crupto.utils

import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun convertTimestampToTime(timestamp: Long?): String{
    if (timestamp == null) return ""
    val stamp = Timestamp(timestamp * 1000)
    val date = Date(stamp.time)
    val pattern = "HH:mm:ss"
    val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
    simpleDateFormat.timeZone = TimeZone.getDefault()
    return simpleDateFormat.format(date)
}