package com.example.workoutwizard.ui.model

import androidx.lifecycle.ViewModel
import com.example.workoutwizard.data.CaloriesUiState
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

    private fun initCaloriesTestData() {
        _uiState.update {
            currentState ->
                currentState.copy(
                    todayCaloriesLimit = 2200,
                    todayCaloriesTaken = 1900
                )
        }
    }
}