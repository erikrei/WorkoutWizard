package com.example.workoutwizard.data

data class UserUiState(
    val email: String = "",
    val createdInitialData: Boolean = false,
    val workouts: List<Workout> = mutableListOf(),
    val foods: List<FoodUiState> = mutableListOf()
)