package com.example.workoutwizard.data

import com.example.workoutwizard.R

object Datasource {
    val mainNavigationItems = listOf(
        MainNavigationType.MAIN_OVERVIEW,
        MainNavigationType.MAIN_WORKOUT,
        MainNavigationType.MAIN_CALORIES
    )

    val forbiddenDestination = listOf(
        "LOGIN",
        "REGISTER",
        "USER_DATA_INTRO"
    )

    val initDataSexOptions = listOf(
        "männlich",
        "weiblich"
    )

    val initDataGoalOptions = listOf(
        "Gewichtsabnahme",
        "Muskelaufbau"
    )

    val initDataGoalSpeedOptions = listOf(
        "langsam",
        "normal",
        "schnell"
    )

    val initDataWorkTypeOptions = listOf(
        "sitzend, wenig körperliche Aktivität",
        "überwiegend sitzend, gehend und stehend",
        "überwiegend stehend und gehend",
        "anstrengende körperliche Arbeit"
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
}