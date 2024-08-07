package com.example.workoutwizard.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.workoutwizard.R
import com.example.workoutwizard.data.Workout
import com.example.workoutwizard.data.WorkoutData
import com.example.workoutwizard.helper.getTodayWorkouts
import com.example.workoutwizard.helper.overviewDateString
import com.example.workoutwizard.ui.components.CaloriesCard
import com.example.workoutwizard.ui.components.HeaderWithContent
import com.example.workoutwizard.ui.components.MainSpacer
import com.example.workoutwizard.ui.components.NavigationHeaderText
import com.example.workoutwizard.ui.components.TextIconButton
import com.example.workoutwizard.ui.components.workout.WorkoutCard
import com.example.workoutwizard.ui.components.workout.WorkoutCardPlan
import com.example.workoutwizard.ui.components.workout.WorkoutCardSmall
import com.example.workoutwizard.ui.model.CaloriesViewModel
import com.example.workoutwizard.ui.model.WorkoutViewModel
import com.example.workoutwizard.ui.theme.WorkoutWizardTheme

@Composable
fun OverviewScreen(
    modifier: Modifier = Modifier,
    workoutViewModel: WorkoutViewModel = viewModel(),
    caloriesViewModel: CaloriesViewModel = viewModel(),
    addWorkoutNavigation: () -> Unit = {},
    addCaloriesNavigation: () -> Unit = {},
    editWorkoutNavigation: (Workout) -> Unit = {},
    viewWorkoutNavigation: (Workout) -> Unit = {}
) {
    val uiState by workoutViewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        OverviewHeader()
        MainSpacer()
        OverviewTodayPlanned(
            plannedWorkouts = getTodayWorkouts(uiState.workouts),
            addWorkoutNavigation = addWorkoutNavigation,
            editWorkoutNavigation = editWorkoutNavigation,
            viewWorkoutNavigation = viewWorkoutNavigation
        )
        MainSpacer()
        OverviewCalories(
            caloriesViewModel = caloriesViewModel,
            addCaloriesNavigation = addCaloriesNavigation
        )
    }
}

@Composable
fun OverviewHeader(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = overviewDateString(),
                style = MaterialTheme.typography.labelMedium
            )
            NavigationHeaderText(
                headerText = R.string.main_overview_header
            )
        }
        IconButton(
            onClick = { /*TODO*/ }
        ) {
            Image(
                painter = painterResource(id = R.drawable.tmp_avatar),
                contentDescription = null
            )
        }
    }
}

@Composable
fun OverviewTodayPlanned(
    modifier: Modifier = Modifier,
    plannedWorkouts: List<Workout>,
    addWorkoutNavigation: () -> Unit = {},
    editWorkoutNavigation: (Workout) -> Unit = {},
    viewWorkoutNavigation: (Workout) -> Unit = {}
) {
    HeaderWithContent(
        headerText = R.string.overview_today_planned,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState())
        ) {
            plannedWorkouts.sortedBy { it.completed }.forEachIndexed {
                    index, workout ->
                val padding =
                    if (index < plannedWorkouts.size - 1) R.dimen.same_content_space
                    else R.dimen.zero_dp
                if (workout.completed) {
                    WorkoutCardPlan(
                        workout = workout,
                        viewWorkoutNavigation = viewWorkoutNavigation,
                        modifier = Modifier
                            .padding(
                                end = dimensionResource(id = padding)
                            )
                    )
                } else {
                    WorkoutCard(
                        workout = workout,
                        editWorkoutNavigation = editWorkoutNavigation,
                        modifier = Modifier
                            .padding(
                                end = dimensionResource(id = padding)
                            )
                    )
                }
            }
        }
        if (plannedWorkouts.isEmpty()) {
            OverviewTodayWorkoutEmpty(
                addWorkoutNavigation = addWorkoutNavigation
            )
        }
    }
}

@Composable
fun OverviewTodayWorkoutEmpty(
    modifier: Modifier = Modifier,
    addWorkoutNavigation: () -> Unit = {}
) {
    HeaderWithContent(
        headerText = R.string.workout_today_empty,
        modifier = modifier,
        style = TextStyle.Default
    ) {
        TextIconButton(
            shape = CutCornerShape(
                topStart = dimensionResource(id = R.dimen.default_cut_corner_radius),
                bottomEnd = dimensionResource(id = R.dimen.default_cut_corner_radius)
            ),
            text = R.string.workout_add,
            icon = R.drawable.add_24,
            onButtonClick = addWorkoutNavigation
        )
    }
}

@Composable
fun OverviewCalories(
    modifier: Modifier = Modifier,
    caloriesViewModel: CaloriesViewModel = viewModel(),
    addCaloriesNavigation: () -> Unit = {}
) {
    val uiState by caloriesViewModel.uiState.collectAsState()

    HeaderWithContent(
        headerText = R.string.overview_today_calories,
        modifier = modifier
    ) {
        CaloriesCard(
            modifier = Modifier
                .fillMaxWidth(),
            todayCaloriesTaken = uiState.todayCaloriesTaken,
            todayCaloriesLimit = uiState.todayCaloriesLimit,
            todayCaloriesBurned = uiState.todayCaloriesBurned,
            addButtonNavigation = addCaloriesNavigation
        )
    }
}

@Preview(showBackground = true, name = "OverviewScreen")
@Composable
fun OverviewScreenPreview() {
    WorkoutWizardTheme {
        OverviewScreen()
    }
}