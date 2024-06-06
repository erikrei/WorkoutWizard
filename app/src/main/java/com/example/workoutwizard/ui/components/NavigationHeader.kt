package com.example.workoutwizard.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.workoutwizard.R
import com.example.workoutwizard.ui.theme.WorkoutWizardTheme

@Composable
fun NavigationHeader(
    modifier: Modifier = Modifier,
    @StringRes headerText: Int,
    sideContent: @Composable () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        NavigationHeaderText(
            headerText = headerText
        )
        sideContent()
    }
}

@Composable
fun NavigationHeaderText(
    modifier: Modifier = Modifier,
    @StringRes headerText: Int,
) {
    Text(
        text = stringResource(id = headerText),
        style = MaterialTheme.typography.headlineLarge,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}

@Preview
@Composable
fun NavigationHeaderPreview() {
    WorkoutWizardTheme {
        NavigationHeader(
            headerText = R.string.main_workout_header,
        )
    }
}