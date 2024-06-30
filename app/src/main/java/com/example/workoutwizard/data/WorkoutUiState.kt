package com.example.workoutwizard.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.workoutwizard.R
import java.time.LocalDate

enum class WorkoutData(
    @StringRes val title: Int,
    @DrawableRes val img: Int,
    @StringRes val pillText: Int,
) {
    JOGGEN(
        title = R.string.workout_card_title_joggen,
        img = R.drawable.joggen,
        pillText = R.string.workout_pill_cardio,
    ),
    PUSHUP(
        title = R.string.workout_card_title_pushups,
        img = R.drawable.pushup,
        pillText = R.string.workout_pill_body,
    ),
    BACK_DUMBELLS(
        title = R.string.workout_card_title_back_dumbells,
        img = R.drawable.back_dumbells,
        pillText = R.string.workout_pill_strength
    ),
    PUSHUP_DUMBELLS(
        title = R.string.workout_card_title_pushups_with_dumbells,
        img = R.drawable.pushup_dumbells,
        pillText = R.string.workout_pill_strength,
    ),
    SITUP(
        title = R.string.workout_card_title_situps,
        img = R.drawable.situp,
        pillText = R.string.workout_pill_body,
    ),
    BICEPS_LONG_DUMBELL(
        title = R.string.workout_card_title_biceps_long_dumbell,
        img = R.drawable.biceps_long_dumbell,
        pillText = R.string.workout_pill_strength
    ),
    YOGA(
        title = R.string.workout_card_title_yoga,
        img = R.drawable.yoga,
        pillText = R.string.workout_pill_body
    )
}

data class Workout(
    val data: WorkoutData,
    var createdAt: LocalDate = LocalDate.now(),
//    val caloriesBurned: Int,
    var completed: Boolean = false
)

data class WorkoutUiState(
    val workouts: List<Workout> = mutableListOf(),
    val todayWorkouts: List<Workout> = mutableListOf(),
    val todayFinished: Int = 2
)