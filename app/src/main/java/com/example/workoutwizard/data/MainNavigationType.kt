package com.example.workoutwizard.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.workoutwizard.R

enum class MainNavigationType(
    @StringRes val mainHeader: Int,
    @DrawableRes val mainIcon: Int
) {
    MAIN_OVERVIEW(
        mainHeader = R.string.main_overview_header,
        mainIcon = R.drawable.home_24
    ),
    MAIN_WORKOUT(
        mainHeader = R.string.main_workout_header,
        mainIcon = R.drawable.exercise_24
    ),
    MAIN_PLAN(
        mainHeader = R.string.main_plan_header,
        mainIcon = R.drawable.list_24
    ),
    MAIN_CALORIES(
        mainHeader = R.string.main_calories_header,
        mainIcon = R.drawable.eat_24
    )
}