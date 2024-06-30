package com.example.workoutwizard.ui.components.workout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.workoutwizard.R
import com.example.workoutwizard.data.Workout
import com.example.workoutwizard.data.WorkoutData
import com.example.workoutwizard.ui.components.ImageFillSizeAlpha
import com.example.workoutwizard.ui.components.TextPill
import com.example.workoutwizard.ui.theme.WorkoutWizardTheme

@Composable
fun WorkoutCard(
    modifier: Modifier = Modifier,
    workout: Workout,
    editWorkoutNavigation: (Workout) -> Unit = {}
) {
    Box(
        modifier = modifier
            .size(
                height = 150.dp,
                width = 300.dp
            )
            .clip(
                shape = RoundedCornerShape(
                    dimensionResource(id = R.dimen.default_border_radius)
                )
            )
    ) {
        ImageFillSizeAlpha(
            image = workout.data.img,
            alpha = .5f,
        )
        Text(
            text = stringResource(id = workout.data.title),
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.workout_card_space_padding)),
            style = MaterialTheme.typography.bodyLarge
        )
        TextPill(
            text = stringResource(id = workout.data.pillText),
            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
            textColor = MaterialTheme.colorScheme.onPrimaryContainer,
            textStyle = MaterialTheme.typography.labelLarge,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(dimensionResource(id = R.dimen.workout_card_space_padding))
        )
        TextButton(
            onClick = { editWorkoutNavigation(workout) },
            modifier = Modifier
                .align(Alignment.BottomEnd)
        ) {
            Text(
                text = stringResource(id = R.string.workout_button_text)
            )
            Icon(
                painter = painterResource(id = R.drawable.arrow_right_24),
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
fun WorkoutCardPreview() {
    WorkoutWizardTheme {
        WorkoutCard(
            workout = Workout(
                data = WorkoutData.SITUP,
            )
        )
    }
}