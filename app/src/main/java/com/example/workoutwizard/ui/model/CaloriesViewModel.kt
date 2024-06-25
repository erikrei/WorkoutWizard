package com.example.workoutwizard.ui.model

import androidx.lifecycle.ViewModel
import com.example.workoutwizard.data.CaloriesUiState
import com.example.workoutwizard.data.WorkoutData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CaloriesViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(CaloriesUiState())
    val uiState: StateFlow<CaloriesUiState> = _uiState.asStateFlow()

    init {
        initCaloriesTestData()
    }

    fun setCaloriesBurned(workouts: List<WorkoutData>) {
        var totalBurned = 0
        workouts.forEach {
            it ->
                totalBurned += it.caloriesBurned
        }
        _uiState.update {
            currentState ->
                currentState.copy(
                    todayCaloriesBurned = totalBurned
                )
        }
    }

    private fun initCaloriesTestData() {
        _uiState.update {
            currentState ->
                currentState.copy(
                    todayCaloriesLimit = 2200,
                    todayCaloriesTaken = 1900,
                    todayCaloriesBurned = 0
                )
        }
    }
}