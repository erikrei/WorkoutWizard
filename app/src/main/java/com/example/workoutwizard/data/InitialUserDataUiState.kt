package com.example.workoutwizard.data

import androidx.annotation.StringRes
import com.example.workoutwizard.R

enum class InitialUserDataStage(
    @StringRes val stageTitle: Int,
    @StringRes val stageText: Int
) {
    USER_DATA_INTRO(
        stageTitle = R.string.init_data_header_intro,
        stageText = R.string.init_data_content_intro
    ),
    USER_DATA_SEX(
        stageTitle = R.string.init_data_header_sex,
        stageText = R.string.init_data_content_sex
    ),
    USER_DATA_AGE(
        stageTitle = R.string.init_data_header_age,
        stageText = R.string.init_data_content_age
    ),
    USER_DATA_HEIGHT(
        stageTitle = R.string.init_data_header_height,
        stageText = R.string.init_data_content_height
    ),
    USER_DATA_WEIGHT(
        stageTitle = R.string.init_data_header_weight,
        stageText = R.string.init_data_content_weight
    ),
    USER_DATA_SLEEP_HOURS(
        stageTitle = R.string.init_data_header_sleep_hours,
        stageText = R.string.init_data_content_sleep_hours
    ),
    USER_DATA_WORK_HOURS(
        stageTitle = R.string.init_data_header_work_hours,
        stageText = R.string.init_data_content_work_hours
    ),
    USER_DATA_WORK_TYPE(
        stageTitle = R.string.init_data_header_work_type,
        stageText = R.string.init_data_content_work_type
    ),
    USER_DATA_WORKOUT_HOURS(
        stageTitle = R.string.init_data_header_workout_hours,
        stageText = R.string.init_data_content_workout_hours
    ),
    USER_DATA_GOAL(
        stageTitle = R.string.init_data_header_goal,
        stageText = R.string.init_data_content_goal
    ),
    USER_DATA_GOAL_SPEED(
        stageTitle = R.string.init_data_header_goal_speed,
        stageText = R.string.init_data_content_goal_speed
    ),
    USER_DATA_OVERVIEW(
        stageTitle = R.string.init_data_header_overview,
        stageText = R.string.init_data_content_overview
    )
}

data class InitialUserDataUiState(
    val sex: Int = 0,
    val age: String = "27",
    val height: String = "178",
    val weight: String = "98",
    val sleepHours: String = "7",
    val workHours: String = "4",
    val workType: Int = 2,
    val workoutHours: String = "2",
    val goal: Int = 0,
    val goalSpeed: Int = 1
)

