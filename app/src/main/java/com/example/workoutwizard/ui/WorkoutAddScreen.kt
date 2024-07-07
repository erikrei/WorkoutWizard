package com.example.workoutwizard.ui

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.workoutwizard.R
import com.example.workoutwizard.data.Datasource
import com.example.workoutwizard.data.Workout
import com.example.workoutwizard.data.WorkoutData
import com.example.workoutwizard.data.WorkoutSection
import com.example.workoutwizard.helper.getLocalDateOfSelectedDay
import com.example.workoutwizard.helper.getMillisecondsBeginningDay
import com.example.workoutwizard.helper.getMillisecondsOfLocalDate
import com.example.workoutwizard.helper.overviewDateStringMilliseconds
import com.example.workoutwizard.ui.components.ExpandedContentWorkoutSections
import com.example.workoutwizard.ui.components.HeaderWithContent
import com.example.workoutwizard.ui.components.MainSpacer
import com.example.workoutwizard.ui.components.NavigationHeader
import com.example.workoutwizard.ui.components.TextPill
import com.example.workoutwizard.ui.components.workout.WorkoutCardSmall
import com.example.workoutwizard.ui.model.WorkoutViewModel
import com.example.workoutwizard.ui.theme.WorkoutWizardTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.UUID

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
    HeaderWithContent(
        headerText = R.string.workout_add_categories_header,
        headerIcon = R.drawable.info_24,
        headerIconDescription = R.string.workout_add_categories_header_icon_description,
        expandedContent = { ExpandedContentWorkoutSections() }
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
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(
                    dimensionResource(id = R.dimen.default_border_radius)
                )
            )
    ) {
        Text(
            text = stringResource(id = section.sectionName),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(
                    dimensionResource(id = R.dimen.card_default_padding)
                )
        )
        TextPill(
            text = section.workoutsList.size.toString(),
            backgroundColor = MaterialTheme.colorScheme.onPrimaryContainer,
            textColor = MaterialTheme.colorScheme.onPrimary,
            textStyle = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(
                    dimensionResource(id = R.dimen.same_content_space)
                )
                .align(Alignment.BottomEnd)
        )

    }
}

@Composable
fun WorkoutSection(
    modifier: Modifier = Modifier,
    @StringRes sectionName: Int,
    onWorkoutClick: (WorkoutData) -> Unit = {}
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
                onWorkoutClick = { onWorkoutClick(it) }
            )
        }
    }
}

@Composable
fun WorkoutSectionWorkoutsList(
    modifier: Modifier = Modifier,
    workouts: List<WorkoutData>,
    onWorkoutClick: (WorkoutData) -> Unit
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
                            onWorkoutClick(workout)
                        }
                )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutAdd(
    modifier: Modifier = Modifier,
    workoutString: String,
    workoutViewModel: WorkoutViewModel,
    onAddClick: () -> Unit = {},
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {
    val workout = WorkoutData.entries.find {
        it.name == workoutString
    }

    if (workout != null) {
        val workoutTitle = stringResource(id = workout.title)
        val date = rememberDatePickerState(
            yearRange = IntRange(
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.YEAR) + 10,
            )
        )
        val buttonText =
            if (date.selectedDateMillis != null) {
                overviewDateStringMilliseconds(
                    milliseconds = date.selectedDateMillis ?: 0
                )
            }
            else stringResource(id = R.string.workout_add_date_select)

        var showDatePicker by remember { mutableStateOf(false) }

        Column(
            modifier = modifier
        ) {
            NavigationHeader(
                headerText = R.string.workout_add
            )
            MainSpacer()
            WorkoutCardSmall(
                workout = workout,
                imageAlpha = .3f,
                modifier = Modifier
                    .fillMaxWidth()
            )
            MainSpacer()
            HeaderWithContent(
                headerText = R.string.workout_add_date
            ) {
                TextButton(
                    onClick = { showDatePicker = true },
                    modifier = Modifier
                        .fillMaxWidth(),
                    border = BorderStroke(
                        width = dimensionResource(id = R.dimen.default_border_width),
                        color = MaterialTheme.colorScheme.primary
                    ),
                    shape = CutCornerShape(
                        topStart = dimensionResource(id = R.dimen.default_cut_corner_radius),
                        bottomEnd = dimensionResource(id = R.dimen.default_cut_corner_radius)
                    )
                ) {
                    Text(
                        text = buttonText
                    )
                }
            }
            MainSpacer()
            Button(
                onClick = {
                    val newWorkout = Workout(
                        data = workout,
                        createdAt = getMillisecondsOfLocalDate(getLocalDateOfSelectedDay(date.selectedDateMillis!!)),
                        workoutID = UUID.randomUUID().toString()
                    )
                    workoutViewModel.addWorkout(newWorkout)
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "$workoutTitle erfolgreich hinzugefügt",
                        )
                    }
                    onAddClick()
                },
                enabled = date.selectedDateMillis != null,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.workout_add)
                )
            }

            if (showDatePicker) {
                WorkoutAddModalBottom(
                    datePickerState = date,
                    setShowDatePickerFalse = {
                        showDatePicker = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutAddModalBottom(
    modifier: Modifier = Modifier,
    datePickerState: DatePickerState,
    setShowDatePickerFalse: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    ModalBottomSheet(
        onDismissRequest = setShowDatePickerFalse,
        sheetState = sheetState,
        modifier = modifier
    ) {
        DatePicker(
            state = datePickerState,
            dateValidator = {
                it >= getMillisecondsBeginningDay()
            },
            showModeToggle = false,
            title = null,
            headline = null
        )
        Button(
            onClick = {
                  scope.launch { sheetState.hide() }.invokeOnCompletion {
                      if (!sheetState.isVisible) {
                          setShowDatePickerFalse()
                      }
                  }
            },
            enabled = datePickerState.selectedDateMillis != null,
            modifier = Modifier
                .fillMaxWidth()
                .systemBarsPadding()
                .padding(
                    dimensionResource(id = R.dimen.spacer_padding)
                )
        ) {
            Text(
                text = stringResource(id = R.string.workout_add_date_select_accept)
            )
        }
    }
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun WorkoutAddScreenPreview() {
    WorkoutWizardTheme {
        WorkoutAddScreen()
    }
}