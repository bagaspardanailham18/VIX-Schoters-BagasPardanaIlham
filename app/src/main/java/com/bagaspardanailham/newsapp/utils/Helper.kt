package com.bagaspardanailham.newsapp.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Helper {
    fun String.withDateFormat(): String {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        val date = format.parse(this) as Date
        return DateFormat.getDateInstance(DateFormat.FULL).format(date)
    }
}