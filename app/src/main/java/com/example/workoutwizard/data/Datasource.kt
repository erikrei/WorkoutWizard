package com.example.workoutwizard.data

import com.example.workoutwizard.R

object Datasource {
    val mainNavigationItems = listOf(
        MainNavigationType.MAIN_OVERVIEW,
        MainNavigationType.MAIN_WORKOUT,
        MainNavigationType.MAIN_PLAN,
        MainNavigationType.MAIN_CALORIES
    )

    val forbiddenDestination = listOf(
        "LOGIN",
        "REGISTER",
        "USER_DATA_INPUT",
        "USER_DATA_OVERVIEW"
    )

    val workoutSections = listOf(
        WorkoutSection(
            sectionName = R.string.workout_add_strength,
            sectionBackgroundImage = R.drawable.strength_without_equipment,
            workoutsList = listOf(
                WorkoutData.SITUP,
                WorkoutData.PUSHUP
            )
        ),
        WorkoutSection(
            sectionName = R.string.workout_add_strength_equipment,
            sectionBackgroundImage = R.drawable.strength_with_equipment,
            workoutsList = listOf(
                WorkoutData.PUSHUP_DUMBELLS,
                WorkoutData.BACK_DUMBELLS,
                WorkoutData.BICEPS_LONG_DUMBELL
            )
        ),
        WorkoutSection(
            sectionName = R.string.workout_add_running,
            sectionBackgroundImage = R.drawable.running,
            workoutsList = listOf(
                WorkoutData.JOGGEN
            )
        ),
        WorkoutSection(
            sectionName = R.string.workout_add_yoga,
            sectionBackgroundImage = R.drawable.yoga,
            workoutsList = listOf(
                WorkoutData.YOGA
            )
        )
    )

    val workoutsToday = mutableListOf(
        WorkoutData.JOGGEN,
        WorkoutData.SITUP,
        WorkoutData.PUSHUP_DUMBELLS
    )

    val workoutsRecent = listOf(
        WorkoutData.SITUP,
        WorkoutData.PUSHUP_DUMBELLS,
    )
}