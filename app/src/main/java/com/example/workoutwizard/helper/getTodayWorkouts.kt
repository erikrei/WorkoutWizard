package com.example.workoutwizard.helper

import com.example.workoutwizard.data.Workout

fun getTodayWorkouts(
    workouts: List<Workout>
): List<Workout> {
    return workouts.filter {
        it.createdAt == getLocalDateOfSelectedDay()
    }
}