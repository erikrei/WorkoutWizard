package com.example.workoutwizard.ui.model

import androidx.lifecycle.ViewModel
import com.example.workoutwizard.data.Workout
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
        createWorkouts()
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
                        if (it.workoutID.toString() == workout.workoutID.toString()) {
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
                    workouts = uiState.value.workouts.filter { it.workoutID.toString() != workoutID }
                )
        }
    }

    private fun createWorkouts() {
        val plannedWorkoutsList = mutableListOf<Workout>()

        for(i in 0..<WorkoutData.entries.size) {
            val workout = Workout(
                data = WorkoutData.entries[i]
            )

            plannedWorkoutsList.add(workout)
        }

        plannedWorkoutsList[0].createdAt = LocalDate.of(2024, 6, 30)
        plannedWorkoutsList[1].createdAt = LocalDate.of(2024, 6, 30)
        plannedWorkoutsList[2].createdAt = LocalDate.of(2024, 6, 29)
        plannedWorkoutsList[3].createdAt = LocalDate.of(2024, 6, 29)
        plannedWorkoutsList[4].createdAt = LocalDate.of(2024, 6, 27)
        plannedWorkoutsList[5].createdAt = LocalDate.of(2024, 6, 26)
        plannedWorkoutsList[6].createdAt = LocalDate.of(2024, 6, 23)

        _uiState.update {
            currentState ->
                currentState.copy(
                    workouts = plannedWorkoutsList
                )
        }
    }
}