package com.example.workoutwizard.helper

import java.time.LocalDate

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