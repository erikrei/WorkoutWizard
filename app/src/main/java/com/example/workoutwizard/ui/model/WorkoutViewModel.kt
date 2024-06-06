package com.example.workoutwizard.ui.model

import androidx.lifecycle.ViewModel
import com.example.workoutwizard.data.Datasource
import com.example.workoutwizard.data.WorkoutData
import com.example.workoutwizard.data.WorkoutUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

class WorkoutViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(WorkoutUiState())
    val uiState: StateFlow<WorkoutUiState> = _uiState.asStateFlow()

    init {
//        initRecentWorkouts()
        createPlannedWorkouts()
        initTodayWorkouts()
    }

    fun addWorkout(workout: WorkoutData) {
        val updatedTodayWorkouts = uiState.value.todayWorkouts.toMutableList()
        updatedTodayWorkouts.add(workout)

        _uiState.update {
            currentState ->
                currentState.copy(
                    todayWorkouts = updatedTodayWorkouts
                )
        }
    }

    private fun initTodayWorkouts() {
        val todayWorkouts = uiState.value.plannedWorkouts.filter {
            it.createdAt.toString() == LocalDate.now().toString()
        }

        _uiState.update {
            currentState ->
                currentState.copy(
                    todayWorkouts = todayWorkouts
                )
        }
    }

    fun removePlannedWorkout(workoutIndex: Int) {
        _uiState.update {
            currentState ->
                currentState.copy(
                    todayWorkouts = uiState.value.todayWorkouts.filterIndexed { index, _ -> index != workoutIndex }
                )
        }
    }

    private fun initRecentWorkouts() {
        _uiState.update {
            currentState ->
                currentState.copy(
                    recentWorkouts = Datasource.workoutsRecent
                )
        }
    }

    private fun createPlannedWorkouts() {
        val plannedWorkoutsList = mutableListOf<WorkoutData>()

        for(i in 0..<WorkoutData.entries.size) {
            val workout = WorkoutData.entries[i]
            if (i >= 1) {
                workout.createdAt = LocalDate.of(
                    2024,
                    LocalDate.now().monthValue,
                    LocalDate.now().dayOfMonth
                )
            } else {
                workout.createdAt = LocalDate.of(
                    2024,
                    5,
                    24
                )
            }

            plannedWorkoutsList.add(workout)
        }

        _uiState.update {
            currentState ->
                currentState.copy(
                    plannedWorkouts = plannedWorkoutsList
                )
        }
    }
}