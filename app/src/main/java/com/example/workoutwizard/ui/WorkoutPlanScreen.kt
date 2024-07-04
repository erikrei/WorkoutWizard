package com.example.workoutwizard.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.workoutwizard.R
import com.example.workoutwizard.data.Workout
import com.example.workoutwizard.helper.getLocalDateOfSelectedDay
import com.example.workoutwizard.helper.getMillisecondsBeginningDay
import com.example.workoutwizard.helper.getMillisecondsOfLocalDate
import com.example.workoutwizard.helper.overviewDateStringMilliseconds
import com.example.workoutwizard.ui.components.HeaderWithContent
import com.example.workoutwizard.ui.components.MainSpacer
import com.example.workoutwizard.ui.components.NavigationHeader
import com.example.workoutwizard.ui.model.WorkoutViewModel
import com.example.workoutwizard.ui.theme.WorkoutWizardTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutPlanScreen(
    modifier: Modifier = Modifier,
    workoutViewModel: WorkoutViewModel = viewModel()
) {
    val uiState by workoutViewModel.uiState.collectAsState()

    val date = rememberDatePickerState(
        yearRange = IntRange(
            2024,
            2100
        )
    )

    val selectedWorkouts =
        if (date.selectedDateMillis != null)
            uiState.workouts.filter {
                it.createdAt == getMillisecondsOfLocalDate(getLocalDateOfSelectedDay(date.selectedDateMillis!!))
            }
        else listOf()

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        NavigationHeader(
            headerText = R.string.sub_workout_plan
        )
        MainSpacer()
        WorkoutPlanSelectedDay(
            datePickerState = date
        )
        MainSpacer()
        WorkoutPlanDatePicker(
            datePickerState = date,
            workouts = uiState.workouts
        )
        if (selectedWorkouts.isNotEmpty()) {
            MainSpacer()
            WorkoutPlanSelectedDayWorkouts(
                workouts = selectedWorkouts
            )
        }
    }
}

@Composable
fun WorkoutPlanSelectedDayWorkouts(
    modifier: Modifier = Modifier,
    workouts: List<Workout>
) {
    HeaderWithContent(
        headerText = R.string.workout_plan_workouts_header,
        modifier = modifier
    ) {
        Column {
            workouts.forEach {
                WorkoutPlanSingleWorkout(
                    workout = it
                )
            }
        }
    }
}

@Composable
fun WorkoutPlanSingleWorkout(
    modifier: Modifier = Modifier,
    workout: Workout
) {
    val statusText =
        if (workout.completed)
            stringResource(id = R.string.workout_completed)
        else stringResource(id = R.string.workout_uncompleted)

    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = workout.data!!.title)
        )
        Text(
            text = statusText
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutPlanSelectedDay(
    modifier: Modifier = Modifier,
    datePickerState: DatePickerState
) {
    val selectedDayText =
        if (datePickerState.selectedDateMillis != null)
            overviewDateStringMilliseconds(datePickerState.selectedDateMillis!!)
        else "Tag auswählen"

    Text(
        text = selectedDayText,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutPlanDatePicker(
    modifier: Modifier = Modifier,
    datePickerState: DatePickerState,
    workouts: List<Workout>
) {
    val workoutDaysSelectable = workouts.map {
        it.createdAt
    }.toSet()

    DatePicker(
        state = datePickerState,
        title = null,
        dateValidator = {
            workoutDaysSelectable.contains(getMillisecondsBeginningDay(it))
        },
        headline = null,
        showModeToggle = false,
        modifier = modifier,
    )
}

@Preview
@Composable
fun WorkoutPlanScreenPreview() {
    WorkoutWizardTheme {
        WorkoutPlanScreen()
    }
}