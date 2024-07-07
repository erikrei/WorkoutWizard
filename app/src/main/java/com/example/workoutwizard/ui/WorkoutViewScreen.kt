package com.example.workoutwizard.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.workoutwizard.R
import com.example.workoutwizard.helper.overviewDateStringMilliseconds
import com.example.workoutwizard.ui.components.HeaderWithContent
import com.example.workoutwizard.ui.components.MainSpacer
import com.example.workoutwizard.ui.components.NavigationHeader
import com.example.workoutwizard.ui.model.WorkoutViewModel

@Composable
fun WorkoutViewScreen(
    modifier: Modifier = Modifier,
    workoutID: String,
    workoutViewModel: WorkoutViewModel
) {
    val uiState by workoutViewModel.uiState.collectAsState()

    val selectedWorkout = uiState.workouts.find {
        it.workoutID == workoutID
    }

    if (selectedWorkout != null) {
        Column(
            modifier = modifier
        ) {
            NavigationHeader(
                headerText = selectedWorkout.data!!.title,
            )
            MainSpacer()
            HeaderWithContent(
                headerText = R.string.workout_plan_completed,
                color = MaterialTheme.colorScheme.primary
            ) {
                Text(
                    text = overviewDateStringMilliseconds(selectedWorkout.createdAt),
                )
            }
            MainSpacer()
            HeaderWithContent(
                headerText = R.string.workout_edit_calories,
                color = MaterialTheme.colorScheme.primary
            ) {
                Text(
                    text = "${selectedWorkout.caloriesBurned} verbrannt"
                )
            }
            MainSpacer()
            HeaderWithContent(
                headerText = R.string.workout_edit_notes,
                color = MaterialTheme.colorScheme.primary
            ) {
                Text(
                    text = selectedWorkout.note.ifEmpty { "Keine Notizen angegeben" }
                )
            }
        }
    }
}