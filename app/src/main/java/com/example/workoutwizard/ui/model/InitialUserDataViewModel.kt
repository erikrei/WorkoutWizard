package com.example.workoutwizard.ui.model

import androidx.lifecycle.ViewModel
import com.example.workoutwizard.data.InitialUserDataUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InitialUserDataViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(InitialUserDataUiState())
    val uiState: StateFlow<InitialUserDataUiState> = _uiState.asStateFlow()

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