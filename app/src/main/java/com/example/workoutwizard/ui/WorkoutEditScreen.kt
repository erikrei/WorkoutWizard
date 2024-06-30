package com.example.workoutwizard.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.workoutwizard.ui.model.WorkoutViewModel

@Composable
fun WorkoutEditScreen(
    modifier: Modifier = Modifier,
    workoutID: String,
    workoutViewModel: WorkoutViewModel
) {
    val uiState by workoutViewModel.uiState.collectAsState()
    
    val selectedWorkout = uiState.workouts.find { 
        it.workoutID.toString() == workoutID
    }
    
    if (selectedWorkout != null) {
        Column(
            modifier = modifier
        ) {
            Text(text = selectedWorkout.data.name)
            Text(text = workoutID)
        }
    }
}