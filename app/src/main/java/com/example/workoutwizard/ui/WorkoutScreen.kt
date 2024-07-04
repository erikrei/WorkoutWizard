package com.example.workoutwizard.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.workoutwizard.R
import com.example.workoutwizard.data.Workout
import com.example.workoutwizard.data.WorkoutUiState
import com.example.workoutwizard.helper.getColorWithAlpha
import com.example.workoutwizard.helper.getTodayWorkouts
import com.example.workoutwizard.helper.percentageTwoNumbers
import com.example.workoutwizard.ui.components.ExpandedContentWorkoutProgress
import com.example.workoutwizard.ui.components.HeaderWithContent
import com.example.workoutwizard.ui.components.MainSpacer
import com.example.workoutwizard.ui.components.NavigationHeader
import com.example.workoutwizard.ui.components.TextIconButton
import com.example.workoutwizard.ui.components.workout.WorkoutCardExpanded
import com.example.workoutwizard.ui.model.WorkoutViewModel
import com.example.workoutwizard.ui.theme.WorkoutWizardTheme
import kotlin.math.floor

@Composable
fun WorkoutScreen(
    modifier: Modifier = Modifier,
    workoutViewModel: WorkoutViewModel = viewModel(),
    addWorkoutNavigation: () -> Unit = {},
    planWorkoutNavigation: () -> Unit = {},
    editWorkoutNavigation: (Workout) -> Unit = {}
) {
    val uiState by workoutViewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        NavigationHeader(
            headerText = R.string.main_workout_header,
            sideContent = {
                WorkoutHeaderSideContent(
                    onAddClick = addWorkoutNavigation,
                    onPlanClick = planWorkoutNavigation
                )
            }
        )
        if (getTodayWorkouts(uiState.workouts).isNotEmpty()) {
            MainSpacer()
            WorkoutProgress(
                workoutUiState = uiState
            )
        }
        MainSpacer()
        WorkoutTodayPlanned(
            workoutViewModel = workoutViewModel,
            addWorkoutNavigation = addWorkoutNavigation,
            planWorkoutNavigation = planWorkoutNavigation,
            workoutUiState = uiState,
            editWorkoutNavigation = editWorkoutNavigation
        )
    }
}

@Composable
fun WorkoutHeaderSideContent(
    modifier: Modifier = Modifier,
    onPlanClick: () -> Unit = {},
    onAddClick: () -> Unit = {}
) {
   Row(
       modifier = modifier
   ) {
        IconButton(
            onClick = onPlanClick
        ) {
            Icon(
                painter = painterResource(id = R.drawable.plan_48),
                contentDescription = stringResource(id = R.string.workout_header_plan_description)
            )
        }
       IconButton(
           onClick = onAddClick
       ) {
           Icon(
               painter = painterResource(id = R.drawable.add_48),
               contentDescription = stringResource(id = R.string.workout_header_add_description)
           )
       }
   }
}

@Composable
fun WorkoutProgress(
    modifier: Modifier = Modifier,
    workoutUiState: WorkoutUiState
) {
    val todayWorkouts = getTodayWorkouts(workoutUiState.workouts)
    val todayCompleted = todayWorkouts.filter { it.completed }.size
    val percentage = percentageTwoNumbers(Pair(todayCompleted, todayWorkouts.size))

    val progressBackgroundColor = when(floor(percentage.second).toInt()) {
        in 0..33 -> Color.Red
        in 34..99 -> Color.Yellow
        else -> Color.Green
    }

    val alphaBackgroundColor = getColorWithAlpha(progressBackgroundColor, .5f)
    
    HeaderWithContent(
        headerText = R.string.workout_progress_header,
        headerIcon = R.drawable.info_24,
        expandedContent = { ExpandedContentWorkoutProgress() },
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        dimensionResource(id = R.dimen.default_box_border_radius)
                    )
                )
        ) {
            Text(
                text = "$todayCompleted von ${todayWorkouts.size} (${percentage.first} %)",
                modifier = Modifier
                    .align(Alignment.Center),
                fontWeight = FontWeight.Bold
            )
            LinearProgressIndicator(
                progress = todayCompleted.toFloat() / getTodayWorkouts(workoutUiState.workouts).size,
                color = progressBackgroundColor,
                trackColor = alphaBackgroundColor,
                modifier = Modifier
                    .matchParentSize()
                    .alpha(.5f)
            )
        }
    }
}

@Composable
fun WorkoutTodayEmptyButtons(
    modifier: Modifier = Modifier,
    addWorkoutNavigation: () -> Unit = {},
    planWorkoutNavigation: () -> Unit = {}
) {
    val buttonModifier = Modifier
        .fillMaxWidth()

    val buttonShape = CutCornerShape(
        topStart = dimensionResource(id = R.dimen.default_cut_corner_radius),
        bottomEnd = dimensionResource(id = R.dimen.default_cut_corner_radius)
    )

    Column(
      modifier = modifier
    ) {
        TextIconButton(
            text = R.string.workout_add,
            icon = R.drawable.add_24,
            shape = buttonShape,
            fillWidth = true,
            modifier = buttonModifier,
            onButtonClick = addWorkoutNavigation
        )
        TextIconButton(
            text = R.string.workout_plan,
            icon = R.drawable.plan_24,
            shape = buttonShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            fillWidth = true,
            modifier = buttonModifier,
            onButtonClick = planWorkoutNavigation
        )
    }
}

@Composable
fun WorkoutTodayPlanned(
    modifier: Modifier = Modifier,
    workoutViewModel: WorkoutViewModel = viewModel(),
    workoutUiState: WorkoutUiState,
    addWorkoutNavigation: () -> Unit = {},
    planWorkoutNavigation: () -> Unit = {},
    editWorkoutNavigation: (Workout) -> Unit = {}
) {
    val todayWorkouts = getTodayWorkouts(workoutUiState.workouts)
    if (todayWorkouts.isEmpty()) {
        HeaderWithContent(
            headerText = R.string.workout_today_empty
        ) {
            WorkoutTodayEmptyButtons(
                addWorkoutNavigation = addWorkoutNavigation,
                planWorkoutNavigation = planWorkoutNavigation
            )
        }
    } else {
        HeaderWithContent(
            headerText = R.string.workout_today_planned,
            modifier = modifier
        ) {
            Column {
                todayWorkouts.forEachIndexed {
                    index, workout ->
                        val bottomPadding =
                            if (index < workoutUiState.workouts.size - 1)
                                dimensionResource(id = R.dimen.same_content_space)
                            else dimensionResource(id = R.dimen.zero_dp)
                            WorkoutCardExpanded(
                                workout = workout,
                                removeWorkout = { workoutViewModel.removeWorkout(workout.workoutID) },
                                editWorkoutNavigation = editWorkoutNavigation,
                                modifier = Modifier
                                    .padding(bottom = bottomPadding)
                            )
                }
            }
        }
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun WorkoutScreenPreview() {
    WorkoutWizardTheme {
        WorkoutScreen()
    }
}
