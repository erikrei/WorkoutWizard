package com.example.workoutwizard.ui.components.workout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.workoutwizard.data.WorkoutData
import com.example.workoutwizard.ui.components.ImageFillSizeAlpha
import com.example.workoutwizard.ui.components.TextPill
import com.example.workoutwizard.ui.theme.WorkoutWizardTheme

@Composable
fun WorkoutCardExpanded(
    modifier: Modifier = Modifier,
    workout: WorkoutData,
    removeWorkout: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(
                shape = RoundedCornerShape(
                    dimensionResource(id = R.dimen.default_border_radius)
                )
            )
    ) {
        ImageFillSizeAlpha(
            image = workout.img,
            alpha = .5f,
        )
        Text(
            text = stringResource(id = workout.title),
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.workout_card_space_padding)),
            style = MaterialTheme.typography.bodyLarge
        )
        TextPill(
            text = stringResource(id = workout.pillText),
            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
            textColor = MaterialTheme.colorScheme.onPrimaryContainer,
            textStyle = MaterialTheme.typography.labelLarge,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(dimensionResource(id = R.dimen.workout_card_space_padding))
        )
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(dimensionResource(id = R.dimen.workout_card_space_padding))
        ) {
            Text(
                text = stringResource(id = R.string.workout_button_text_start)
            )
        }
        IconButton(
            onClick = removeWorkout,
            modifier = Modifier
                .align(Alignment.TopEnd)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.remove_24),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Preview
@Composable
fun WorkoutCardExpandedPreview() {
    WorkoutWizardTheme {
        WorkoutCardExpanded(workout = WorkoutData.SITUP)
    }
}