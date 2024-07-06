package com.example.workoutwizard.helper

import com.example.workoutwizard.data.InitialUserDataUiState

fun checkIfInitialDataCanComplete(
    uiState: InitialUserDataUiState
): Boolean {
    return (
        uiState.sex != -1 &&
        uiState.age.isNotEmpty() &&
        uiState.height.isNotEmpty() &&
        uiState.weight.isNotEmpty() &&
        uiState.sleepHours.isNotEmpty() &&
        uiState.workHours.isNotEmpty() &&
        uiState.workType != -1 &&
        uiState.workoutHours.isNotEmpty() &&
        uiState.goal != -1 &&
        uiState.goalSpeed != -1
    )
}