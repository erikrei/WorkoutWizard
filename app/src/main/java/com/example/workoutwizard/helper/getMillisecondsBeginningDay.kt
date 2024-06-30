package com.example.workoutwizard.helper

import java.util.Calendar

fun getMillisecondsBeginningDay(
    milliseconds: Long = Calendar.getInstance().timeInMillis
): Long {
    val cal = Calendar.getInstance()
    cal.timeInMillis = milliseconds

    cal.set(Calendar.HOUR_OF_DAY, 0)
    cal.set(Calendar.MINUTE, 0)
    cal.set(Calendar.SECOND, 0)
    cal.set(Calendar.MILLISECOND, 0)

    return cal.timeInMillis
}