package com.example.workoutwizard.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.workoutwizard.R
import com.example.workoutwizard.data.Datasource
import com.example.workoutwizard.data.WorkoutData
import com.example.workoutwizard.data.WorkoutSection
import com.example.workoutwizard.data.WorkoutUiState
import com.example.workoutwizard.ui.components.ImageFillSizeAlpha
import com.example.workoutwizard.ui.components.MainSpacer
import com.example.workoutwizard.ui.components.NavigationHeader
import com.example.workoutwizard.ui.components.TextPill
import com.example.workoutwizard.ui.components.workout.WorkoutCardSmall
import com.example.workoutwizard.ui.model.WorkoutViewModel
import com.example.workoutwizard.ui.theme.WorkoutWizardTheme

@Composable
fun WorkoutAddScreen(
    modifier: Modifier = Modifier,
    navigateToSection: (WorkoutSection) -> Unit = {},
) {
    val workoutSections = Datasource.workoutSections

    Column(
        modifier = modifier
    ) {
        NavigationHeader(
            headerText = R.string.sub_workout_add,
            modifier = Modifier
                .padding(
                    bottom = dimensionResource(id = R.dimen.same_content_space)
                )
        )
        MainSpacer()
        WorkoutAddSections(
            sections = workoutSections,
            navigateToSection = navigateToSection

        )
    }
}

@Composable
fun WorkoutAddSections(
    modifier: Modifier = Modifier,
    sections: List<WorkoutSection>,
    navigateToSection: (WorkoutSection) -> Unit = {}
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.grid_padding)
        ),
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.grid_padding)
        ),
        modifier = modifier
    ) {
        items(sections) {
            section ->
                WorkoutAddHeaderCard(
                    section = section,
                    modifier = Modifier
                        .clickable {
                            navigateToSection(section)
                        }
                )
        }
    }
}

@Composable
fun WorkoutAddHeaderCard(
    modifier: Modifier = Modifier,
    section: WorkoutSection,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(
                RoundedCornerShape(
                    dimensionResource(id = R.dimen.default_border_radius)
                )
            )
    ) {
        ImageFillSizeAlpha(
            image = section.sectionBackgroundImage,
            alpha = .3f,
            modifier = Modifier
                .matchParentSize()
        )
        Text(
            text = stringResource(id = section.sectionName),
            modifier = Modifier
                .padding(
                    dimensionResource(id = R.dimen.card_default_padding)
                )
        )

        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    dimensionResource(id = R.dimen.card_default_padding)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val workoutsSize = section.workoutsList.size
            TextPill(
                text = workoutsSize.toString(),
                modifier = Modifier
                    .padding(
                        end = dimensionResource(id = R.dimen.same_content_space)
                    )
            )
            Text(
                text = if (workoutsSize > 1) "Workouts" else "Workout",
            )
        }

    }
}

@Composable
fun WorkoutSection(
    modifier: Modifier = Modifier,
    @StringRes sectionName: Int,
    workoutViewModel: WorkoutViewModel = viewModel(),
    navigateToWorkoutScreen: () -> Unit = {}
) {
    val section = Datasource.workoutSections.find {
        it.sectionName == sectionName
    }

    if (section != null) {
        Column(
            modifier = modifier
        ) {
            NavigationHeader(
                headerText = sectionName
            )
            MainSpacer()
            WorkoutSectionWorkoutsList(
                workouts = section.workoutsList,
                workoutViewModel = workoutViewModel,
                navigateToWorkoutScreen = navigateToWorkoutScreen
            )
        }
    }
}

@Composable
fun WorkoutSectionWorkoutsList(
    modifier: Modifier = Modifier,
    workouts: List<WorkoutData>,
    workoutViewModel: WorkoutViewModel = viewModel(),
    navigateToWorkoutScreen: () -> Unit = {}
) {
    Column(
        modifier = modifier
    ) {
        workouts.forEachIndexed {
            index, workout ->
                val paddingBottom =
                    if (index < workouts.size - 1)
                        dimensionResource(id = R.dimen.same_content_space)
                    else dimensionResource(id = R.dimen.zero_dp)

                WorkoutCardSmall(
                    workout = workout,
                    imageAlpha = .3f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = paddingBottom)
                        .clickable {
                            workoutViewModel.addWorkout(workout)
                            navigateToWorkoutScreen()
                        }
                )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun WorkoutAddScreenPreview() {
    WorkoutWizardTheme {
        WorkoutAddScreen()
    }
}