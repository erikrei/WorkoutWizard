package com.example.workoutwizard.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.workoutwizard.R
import com.example.workoutwizard.helper.overviewDateString
import com.example.workoutwizard.ui.components.MainSpacer
import com.example.workoutwizard.ui.components.NavigationHeader
import com.example.workoutwizard.ui.model.WorkoutViewModel
import java.time.LocalDate

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
            NavigationHeader(
                headerText = selectedWorkout.data.title
            )
            MainSpacer()
            WorkoutEditDate(
                workoutDate = selectedWorkout.createdAt 
            )
        }
    }
}

@Composable
fun WorkoutEditDate(
    modifier: Modifier = Modifier,
    workoutDate: LocalDate
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${stringResource(id = R.string.workout_edit_planned)}:",
            modifier = Modifier
                .padding(
                    end = dimensionResource(id = R.dimen.same_content_space)
                )
        )
        Text(
            text = overviewDateString(workoutDate),
            fontWeight = FontWeight.Bold
        )
    }
}