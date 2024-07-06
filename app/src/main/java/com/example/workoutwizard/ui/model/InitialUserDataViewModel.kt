package com.example.workoutwizard.ui.model

import androidx.lifecycle.ViewModel
import com.example.workoutwizard.data.InitialUserDataStage
import com.example.workoutwizard.data.InitialUserDataUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InitialUserDataViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(InitialUserDataUiState())
    val uiState: StateFlow<InitialUserDataUiState> = _uiState.asStateFlow()

    fun changeGoalSpeedType(goalSpeedTypeInput: String) {
        val goalSpeedTypeInt = when (goalSpeedTypeInput) {
            "langsam" -> 0
            "normal" -> 1
            "schnell" -> 2
            else -> -1
        }

        _uiState.update {
                currentState ->
            currentState.copy(
                goalSpeed = goalSpeedTypeInt
            )
        }
    }
    fun changeGoalType(goalTypeInput: String) {
        val goalTypeInt = when (goalTypeInput) {
            "Gewichtsabnahme" -> 0
            "Muskelaufbau" -> 1
            else -> -1
        }

        _uiState.update {
                currentState ->
            currentState.copy(
                goal = goalTypeInt
            )
        }
    }
    fun changeWorkType(workTypeInput: String) {
        val workTypeInt = when (workTypeInput) {
            "sitzend, wenig körperliche Aktivität" -> 0
            "überwiegend sitzend, gehend und stehend" -> 1
            "überwiegend stehend und gehend" -> 2
            "anstrengende körperliche Arbeit" -> 3
            else -> -1
        }

        _uiState.update {
            currentState ->
                currentState.copy(
                    workType = workTypeInt
                )
        }
    }

    fun changeWorkoutHours(workoutHoursInput: String) {
        _uiState.update {
                currentState ->
            currentState.copy(
                workoutHours = workoutHoursInput
            )
        }
    }
    fun changeWorkHours(workHoursInput: String) {
        _uiState.update {
                currentState ->
            currentState.copy(
                workHours = workHoursInput
            )
        }
    }
    fun changeSleepHours(sleepHoursInput: String) {
        _uiState.update {
            currentState ->
                currentState.copy(
                    sleepHours = sleepHoursInput
                )
        }
    }
    fun changeSex(sexInput: String) {
        val sexInt = when (sexInput) {
            "männlich" -> 0
            "weiblich" -> 1
            else -> -1
        }

        _uiState.update {
            currentState ->
                currentState.copy(
                    sex = sexInt
                )
        }
    }

    fun changeAge(ageInput: String) {
        _uiState.update {
            currentState ->
                currentState.copy(
                    age = ageInput
                )
        }
    }

    fun changeHeight(heightInput: String) {
        _uiState.update {
            currentState ->
                currentState.copy(
                    height = heightInput
                )
        }
    }

    fun changeWeight(weightInput: String) {
        _uiState.update {
            currentState ->
                currentState.copy(
                    weight = weightInput
                )
        }
    }
}