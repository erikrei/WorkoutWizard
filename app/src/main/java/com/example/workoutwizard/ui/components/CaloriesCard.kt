package com.example.workoutwizard.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.example.workoutwizard.helper.getColorWithAlpha
import com.example.workoutwizard.helper.percentageTwoNumbers
import com.example.workoutwizard.ui.theme.WorkoutWizardTheme

@Composable
fun CaloriesCard(
    modifier: Modifier = Modifier,
    todayCaloriesTaken: Int,
    todayCaloriesLimit: Int
) {
    Box(
        modifier = modifier
            .clip(
                RoundedCornerShape(
                    dimensionResource(id = R.dimen.default_border_radius)
                )
            )
            .background(
                color = MaterialTheme.colorScheme.primaryContainer
            )
    ) {
        val percentageTaken = percentageTwoNumbers(
            Pair(todayCaloriesTaken, todayCaloriesLimit)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    dimensionResource(id = R.dimen.container_padding)
                ),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CaloriesCardSideText(
                    sideTextHeader = R.string.overview_calories_remainder,
                    sideText = todayCaloriesLimit - todayCaloriesTaken
                )
                CaloriesCardPercentage(
                    percentage = percentageTaken,
                    caloriesTaken = todayCaloriesTaken
                )
                CaloriesCardSideText(
                    sideTextHeader = R.string.overview_calories_burned,
                    sideText = 0
                )
            }

            MainSpacer(
                spaceSize = R.dimen.same_content_space
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(id = R.dimen.container_padding)
                    )
                    .clickable { },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .border(
                            width = dimensionResource(id = R.dimen.default_border_width),
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            shape = RoundedCornerShape(
                                dimensionResource(id = R.dimen.default_box_border_radius)
                            )
                        )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.add_24),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(2.dp),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                Text(
                    text = stringResource(id = R.string.overview_calories_add),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier
                        .padding(
                            start = dimensionResource(id = R.dimen.same_content_space)
                        )
                )
            }
        }
    }
}

@Composable
fun CaloriesCardSideText(
    modifier: Modifier = Modifier,
    @StringRes sideTextHeader: Int,
    sideText: Int
) {
    Column(
        modifier = modifier
            .width(dimensionResource(id = R.dimen.calories_card_side_text_width)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = sideText.toString(),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = stringResource(id = sideTextHeader),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

    }
}

@Composable
fun CaloriesCardPercentage(
    modifier: Modifier = Modifier,
    percentage: Pair<String, Double>,
    caloriesTaken: Int
) {
    Box(
        modifier = modifier
            .size(120.dp),
        contentAlignment = Alignment.Center
    ) {
        CaloriesCardSideText(
            sideTextHeader = R.string.overview_calories_today_taken,
            sideText = caloriesTaken
        )
        CircularProgressIndicator(
            progress = percentage.second.toFloat() / 100,
            color = MaterialTheme.colorScheme.primary,
            trackColor = getColorWithAlpha(MaterialTheme.colorScheme.primary, .5f),
            modifier = Modifier
                .matchParentSize()
        )
    }
}

@Preview
@Composable
fun CaloriesCardPreview() {
    WorkoutWizardTheme {
        CaloriesCard(
            todayCaloriesTaken = 1900,
            todayCaloriesLimit = 2200
        )
    }
}