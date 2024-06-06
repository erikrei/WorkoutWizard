package com.example.workoutwizard.ui.components.workout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.workoutwizard.R
import com.example.workoutwizard.data.WorkoutData
import com.example.workoutwizard.ui.components.ImageFillSizeAlpha
import com.example.workoutwizard.ui.components.TextPill
import com.example.workoutwizard.ui.theme.WorkoutWizardTheme

@Composable
fun WorkoutCardSmall(
    modifier: Modifier = Modifier,
    imageAlpha: Float = .5f,
    workout: WorkoutData,
) {
    Box(
        modifier = modifier
            .size(
                height = 50.dp,
                width = 250.dp
            )
            .clip(
                RoundedCornerShape(
                    dimensionResource(id = R.dimen.default_border_radius)
                )
            )
    ) {
        ImageFillSizeAlpha(
            image = workout.img,
            contentScale = ContentScale.None,
            alpha = imageAlpha
        )
        Row(
            modifier = Modifier
                .padding(
                    dimensionResource(id = R.dimen.card_default_padding)
                )
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = workout.title),
            )
            TextPill(
                text = stringResource(id = workout.pillText)
            )
        }
    }
}

@Preview
@Composable
fun WorkoutCardSmallPreview() {
    WorkoutWizardTheme {
        WorkoutCardSmall(workout = WorkoutData.BICEPS_LONG_DUMBELL)
    }
}