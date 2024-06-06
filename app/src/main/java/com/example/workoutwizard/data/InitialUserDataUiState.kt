package com.example.workoutwizard.data

enum class InitialUserDataStage {
    USER_DATA_INPUT,
    USER_DATA_OVERVIEW
}

data class InitialUserDataUiState(
    val age: String = "",
    val height: String = "",
    val weight: String = ""
)