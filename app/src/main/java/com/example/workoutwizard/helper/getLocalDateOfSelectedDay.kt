package com.example.workoutwizard.helper

import java.time.LocalDate
import java.util.Calendar

fun getLocalDateOfSelectedDay(
    milliseconds: Long = Calendar.getInstance().timeInMillis
): LocalDate {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = milliseconds

    val day = calendar.get(Calendar.DATE)
    val month = calendar.get(Calendar.MONTH) + 1
    val year = calendar.get(Calendar.YEAR)

    return LocalDate.of(
        year, month, day
    )
}