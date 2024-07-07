package com.example.workoutwizard.helper

import com.example.workoutwizard.data.InitialUserDataUiState

fun getCaloriesAllowed(
    data: InitialUserDataUiState
): Int {
    var caloriesBase = 0.0
    var caloriesPerformance = 0.0
    var totalCalories = 0.0

    caloriesBase = if (data.sex == 0) {
//        66.47 + (13.7 * data.weight.toDouble()) + (5 * data.height.toInt()) - (6.8 * data.age.toInt())
        (9.99 * data.weight.toDouble()) + (6.25 * data.height.toInt()) - (4.92 * data.age.toInt()) + 5
    } else {
//        655.1 + (9.6 * data.weight.toDouble()) + (1.8 * data.height.toInt()) - (4.7 * data.age.toInt())
        (9.99 * data.weight.toDouble()) + (6.25 * data.height.toInt()) - (4.92 * data.age.toInt()) - 161
    }

    // MET-Wert berechnen
    val sleepHoursMET = data.sleepHours.toDouble() * 0.9
    val workHoursMET = when (data.workType) {
        0 -> data.workHours.toDouble() * 1.4
        1 -> data.workHours.toDouble() * 2
        2 -> data.workHours.toDouble() * 3
        3 -> data.workHours.toDouble() * 4.5
        else -> data.workHours.toDouble()
    }
    val workoutHoursMET = data.workoutHours.toDouble() * 4.5

    val totalMET = (sleepHoursMET + workHoursMET + workoutHoursMET) / 24

    caloriesPerformance = caloriesBase * (totalMET - 1)

    totalCalories = caloriesBase + caloriesPerformance

    val extraCalories = when (data.goalSpeed) {
        0 -> 100
        1 -> 200
        2 -> 300
        else -> 0
    }

    totalCalories =
        if (data.goal == 0)
            totalCalories - extraCalories
        else totalCalories + extraCalories

    return totalCalories.toInt()
}