package com.example.workoutwizard.helper

import java.time.LocalDate
import java.util.Calendar

fun overviewDateString(
    date: LocalDate = LocalDate.now()
): String {
    val weekday = when (date.dayOfWeek.name) {
        "MONDAY" -> "Montag"
        "TUESDAY" -> "Dienstag"
        "WEDNESDAY" -> "Mittwoch"
        "THURSDAY" -> "Donnerstag"
        "FRIDAY" -> "Freitag"
        "SATURDAY" -> "Samstag"
        "SUNDAY" -> "Sonntag"
        else -> ""
    }

    val day = date.dayOfMonth

    val month = when(date.month.name) {
        "JANUARY" -> "Januar"
        "FEBRUARY" -> "Februar"
        "MARCH" -> "März"
        "APRIL" -> "April"
        "MAY" -> "Mai"
        "JUNE" -> "Juni"
        "JULY" -> "Juli"
        "AUGUST" -> "August"
        "SEPTEMBER" -> "September"
        "OCTOBER" -> "Oktober"
        "NOVEMBER" -> "November"
        "DECEMBER" -> "Dezember"
        else -> ""
    }

    return "$weekday, $day. $month"
}

fun overviewDateStringMilliseconds(
    milliseconds: Long
): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = milliseconds

    val weekday = calendar.get(Calendar.DATE)

    val month = when (calendar.get(Calendar.MONTH)) {
        0 -> "Januar"
        1 -> "Februar"
        2 -> "März"
        3 -> "April"
        4 -> "Mai"
        5 -> "Juni"
        6 -> "Juli"
        7 -> "August"
        8 -> "September"
        9 -> "Oktober"
        10 -> "November"
        11 -> "Dezember"
        else -> ""
    }

    val year = calendar.get(Calendar.YEAR)

    return "$weekday. $month $year"
}