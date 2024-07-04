package com.example.workoutwizard.ui.model

import androidx.lifecycle.ViewModel
import com.example.workoutwizard.data.Workout
import com.example.workoutwizard.data.WorkoutUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class WorkoutViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(WorkoutUiState())
    val uiState: StateFlow<WorkoutUiState> = _uiState.asStateFlow()

    fun initWorkoutData(workouts: List<Workout>) {
        _uiState.update {
            currentState ->
                currentState.copy(
                    workouts = workouts
                )
        }
    }

    fun addWorkout(workout: Workout) {
        _uiState.update {
            currentState ->
                currentState.copy(
                    workouts = uiState.value.workouts.plus(workout)
                )
        }
    }

    fun editWorkout(workout: Workout) {
        _uiState.update {
            currentState ->
                currentState.copy(
                    workouts = uiState.value.workouts.map {
                        if (it.workoutID == workout.workoutID) {
                            workout
                        } else {
                            it
                        }
                    }
                )
        }
    }

    fun removeWorkout(workoutID: String) {
        _uiState.update {
            currentState ->
                currentState.copy(
                    workouts = uiState.value.workouts.filter { it.workoutID != workoutID }
                )
        }
    }
}