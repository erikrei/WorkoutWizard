package com.example.workoutwizard.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
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
import com.example.workoutwizard.ui.components.workout.WorkoutCardPlan
import com.example.workoutwizard.ui.model.WorkoutViewModel
import com.example.workoutwizard.ui.theme.WorkoutWizardTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutPlanScreen(
    modifier: Modifier = Modifier,
    workoutViewModel: WorkoutViewModel = viewModel(),
    viewWorkoutNavigation: (Workout) -> Unit = {}
) {
    val uiState by workoutViewModel.uiState.collectAsState()
    var expandDatePicker by remember { mutableStateOf(true) }

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
            datePickerState = date,
            showDatePicker = expandDatePicker,
            onChangeVisibility = { expandDatePicker = !expandDatePicker }
        )
        MainSpacer()
        AnimatedVisibility(
            visible = expandDatePicker
        ) {
            WorkoutPlanDatePicker(
                datePickerState = date,
                workouts = uiState.workouts
            )
        }
        if (selectedWorkouts.isNotEmpty()) {
            MainSpacer()
            WorkoutPlanSelectedDayWorkouts(
                workouts = selectedWorkouts,
                viewWorkoutNavigation = viewWorkoutNavigation
            )
        }
    }
}

@Composable
fun WorkoutPlanSelectedDayWorkouts(
    modifier: Modifier = Modifier,
    workouts: List<Workout>,
    viewWorkoutNavigation: (Workout) -> Unit = {}
) {
    HeaderWithContent(
        headerText = R.string.workout_plan_workouts_header,
        modifier = modifier
    ) {
        Column {
            workouts.sortedBy { it.completed }.forEach {
                WorkoutPlanSingleWorkout(
                    workout = it,
                    modifier = Modifier
                        .padding(
                            bottom = dimensionResource(id = R.dimen.same_content_space)
                        ),
                    viewWorkoutNavigation = viewWorkoutNavigation
                )
            }
        }
    }
}

@Composable
fun WorkoutPlanSingleWorkout(
    modifier: Modifier = Modifier,
    workout: Workout,
    viewWorkoutNavigation: (Workout) -> Unit = {}
) {
    WorkoutCardPlan(
        workout = workout,
        modifier = modifier
            .fillMaxWidth(),
        viewWorkoutNavigation = viewWorkoutNavigation
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutPlanSelectedDay(
    modifier: Modifier = Modifier,
    datePickerState: DatePickerState,
    showDatePicker: Boolean,
    onChangeVisibility: () -> Unit = {}
) {
    val selectedDayText =
        if (datePickerState.selectedDateMillis != null)
            overviewDateStringMilliseconds(datePickerState.selectedDateMillis!!)
        else "Tag auswählen"

    val showIcon =
        if (showDatePicker)
            R.drawable.arrow_up_24
        else R.drawable.arrow_down_24

    val showIconContentDescription =
        if (showDatePicker)
            R.string.workout_plan_hide_picker
        else R.string.workout_plan_show_picker

    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = selectedDayText,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )
        IconButton(
            onClick = onChangeVisibility
        ) {
            Icon(
                painter = painterResource(id = showIcon),
                contentDescription = stringResource(id = showIconContentDescription)
            )
        }
    }
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