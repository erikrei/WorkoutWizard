package com.example.workoutwizard.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.workoutwizard.R
import com.example.workoutwizard.helper.overviewDateString
import com.example.workoutwizard.ui.components.HeaderWithContent
import com.example.workoutwizard.ui.components.InputField
import com.example.workoutwizard.ui.components.MainSpacer
import com.example.workoutwizard.ui.components.NavigationHeader
import com.example.workoutwizard.ui.model.WorkoutViewModel
import java.time.LocalDate

@Composable
fun WorkoutEditScreen(
    modifier: Modifier = Modifier,
    workoutID: String,
    workoutViewModel: WorkoutViewModel,
    onWorkoutChange: () -> Unit = {}
) {
    val uiState by workoutViewModel.uiState.collectAsState()

    val selectedWorkout = uiState.workouts.find { 
        it.workoutID.toString() == workoutID
    }

    var note by remember { mutableStateOf(selectedWorkout?.note ?: "") }

    
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
            MainSpacer()
            WorkoutEditNote(
                value = note,
                onValueChange = { note = it }
            )
            MainSpacer()
            WorkoutEditNote(
                onWorkoutChange = {
                    selectedWorkout.completed = true
                    selectedWorkout.note = note
                    workoutViewModel.editWorkout(selectedWorkout)
                    onWorkoutChange()
                }
            )
        }
    }
}

@Composable
fun WorkoutEditNote(
    modifier: Modifier = Modifier,
    onWorkoutChange: () -> Unit = {}
) {
    Button(
        onClick = onWorkoutChange,
        modifier = modifier
            .fillMaxWidth(),
        shape = CutCornerShape(
            topStart = dimensionResource(id = R.dimen.default_cut_corner_radius),
            bottomEnd = dimensionResource(id = R.dimen.default_cut_corner_radius)
        )
    ) {
        Text(
            text = stringResource(id = R.string.workout_edit_accept)
        )
    }
}

@Composable
fun WorkoutEditNote(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit = {}
) {

    HeaderWithContent(
        headerText = R.string.workout_edit_notes,
        modifier = modifier
    ) {
        InputField(
            value = value,
            onValueChange = onValueChange,
            label = R.string.workout_edit_notes,
            singleLine = false,
            isLast = true,
            modifier = Modifier
                .fillMaxWidth()
        )
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