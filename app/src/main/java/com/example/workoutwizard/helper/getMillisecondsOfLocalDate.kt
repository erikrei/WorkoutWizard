package com.example.workoutwizard.helper

import java.time.LocalDate
import java.util.Calendar

fun getMillisecondsOfLocalDate(
    date: LocalDate
): Long {
    val calendar = Calendar.getInstance()
    calendar.set(date.year, date.monthValue - 1, date.dayOfMonth)

    return getMillisecondsBeginningDay(calendar.timeInMillis)
}