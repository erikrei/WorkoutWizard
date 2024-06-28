package com.example.workoutwizard.data

import androidx.annotation.StringRes
import com.example.workoutwizard.R

enum class SubNavigationType(
    @StringRes val navigationHeader: Int,
) {
    SUB_WORKOUT_ADD(
        navigationHeader = R.string.sub_workout_add
    ),
    SUB_WORKOUT_PLAN(
        navigationHeader = R.string.sub_workout_plan
    ),
    SUB_CALORIES_ADD(
        navigationHeader = R.string.calories_add
    )
}