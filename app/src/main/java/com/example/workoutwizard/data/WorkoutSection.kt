package com.example.workoutwizard.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class WorkoutSection(
    @StringRes val sectionName: Int,
    @DrawableRes val sectionBackgroundImage: Int,
    val workoutsList: List<WorkoutData>
)
