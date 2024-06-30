package com.example.workoutwizard.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.workoutwizard.R
import com.example.workoutwizard.helper.overviewDateStringMilliseconds
import com.example.workoutwizard.ui.components.MainSpacer
import com.example.workoutwizard.ui.components.NavigationHeader
import com.example.workoutwizard.ui.theme.WorkoutWizardTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutPlanScreen(
    modifier: Modifier = Modifier
) {
    val date = rememberDatePickerState(
        yearRange = IntRange(
            2024,
            2100
        )
    )

    Column(
        modifier = modifier
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
            datePickerState = date
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
    datePickerState: DatePickerState
) {
    DatePicker(
        state = datePickerState,
        title = null,
        headline = null,
        showModeToggle = false,
        modifier = modifier
    )
}

@Preview
@Composable
fun WorkoutPlanScreenPreview() {
    WorkoutWizardTheme {
        WorkoutPlanScreen()
    }
}