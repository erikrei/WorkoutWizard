package com.example.workoutwizard.ui.model

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.workoutwizard.data.Datasource
import com.example.workoutwizard.data.WorkoutData
import com.example.workoutwizard.data.WorkoutUiState
import com.example.workoutwizard.helper.getLocalDateOfSelectedDay
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
        initTodayWorkouts()
    }

    fun addWorkout(workout: WorkoutData) {
        if (workout.createdAt == getLocalDateOfSelectedDay()) {
            _uiState.update {
                currentState ->
                    currentState.copy(
                        todayWorkouts = uiState.value.todayWorkouts.plus(workout)
                    )
            }
        } else {
            _uiState.update {
                    currentState ->
                currentState.copy(
                    workouts = uiState.value.workouts.plus(workout)
                )
            }
        }
    }

    private fun initTodayWorkouts() {
        val todayWorkouts = uiState.value.workouts.filter {
            it.createdAt.toString() == LocalDate.now().toString()
        }

        _uiState.update {
            currentState ->
                currentState.copy(
                    todayWorkouts = todayWorkouts
                )
        }
    }

    fun removeTodayWorkout(workoutIndex: Int) {
        _uiState.update {
            currentState ->
                currentState.copy(
                    todayWorkouts = uiState.value.todayWorkouts.filterIndexed { index, _ -> index != workoutIndex }
                )
        }
    }

    private fun createWorkouts() {
        val plannedWorkoutsList = mutableListOf<WorkoutData>()

        for(i in 0..<WorkoutData.entries.size) {
            val workout = WorkoutData.entries[i]

            plannedWorkoutsList.add(workout)
        }

        plannedWorkoutsList[0].createdAt = LocalDate.of(2024, 6, 29)
        plannedWorkoutsList[1].createdAt = LocalDate.of(2024, 6, 2)
        plannedWorkoutsList[2].createdAt = LocalDate.of(2024, 6, 29)
        plannedWorkoutsList[3].createdAt = LocalDate.of(2024, 6, 29)
        plannedWorkoutsList[4].createdAt = LocalDate.of(2024, 6, 27)
        plannedWorkoutsList[5].createdAt = LocalDate.of(2024, 6, 26)
        plannedWorkoutsList[6].createdAt = LocalDate.of(2024, 6, 23)

        for(i in 0..<plannedWorkoutsList.size) {
            Log.i("WorkoutViewModel", plannedWorkoutsList[i].createdAt.toString())
        }

        _uiState.update {
            currentState ->
                currentState.copy(
                    workouts = plannedWorkoutsList
                )
        }
    }
}