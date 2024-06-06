package com.example.workoutwizard.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.workoutwizard.R
import com.example.workoutwizard.ui.components.NavigationHeader
import com.example.workoutwizard.ui.theme.WorkoutWizardTheme

@Composable
fun WorkoutHistoryScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        NavigationHeader(
            headerText = R.string.sub_workout_history
        )
    }
}

@Preview
@Composable
fun WorkoutHistoryScreenPreview() {
    WorkoutWizardTheme {
        WorkoutHistoryScreen()
    }
}