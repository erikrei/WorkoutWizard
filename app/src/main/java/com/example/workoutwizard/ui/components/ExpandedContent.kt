package com.example.workoutwizard.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.workoutwizard.R

@Composable
fun ExpandedContentWorkoutProgress(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(
                    dimensionResource(id = R.dimen.default_box_border_radius)
                )
            )
    ) {
        Text(
            text = stringResource(id = R.string.workout_progress_expanded_text),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier
                .padding(
                    dimensionResource(id = R.dimen.container_padding)
                )
        )
    }
}

@Composable
fun ExpandedContentWorkoutSections(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(
                    dimensionResource(id = R.dimen.default_box_border_radius)
                )
            )
    ) {
        Text(
            text = stringResource(id = R.string.workout_add_categories_expanded_text),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier
                .padding(
                    dimensionResource(id = R.dimen.container_padding)
                )
        )
    }
}