package com.example.workoutwizard.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.workoutwizard.R

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
    val data: WorkoutData? = null,
    var createdAt: Long = 0,
    val caloriesBurned: Int = 0,
    var completed: Boolean = false,
    val workoutID: String = "",
    var note: String = ""
)

data class WorkoutUiState(
    val workouts: List<Workout> = mutableListOf()
)