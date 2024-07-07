package com.example.workoutwizard.ui

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.workoutwizard.R
import com.example.workoutwizard.data.Datasource
import com.example.workoutwizard.data.InitialUserDataStage
import com.example.workoutwizard.data.InitialUserDataUiState
import com.example.workoutwizard.helper.checkIfInitialDataCanComplete
import com.example.workoutwizard.ui.components.HeaderWithContent
import com.example.workoutwizard.ui.components.MainSpacer
import com.example.workoutwizard.ui.components.SelectMenu
import com.example.workoutwizard.ui.components.TextButton
import com.example.workoutwizard.ui.model.InitialUserDataViewModel
import com.example.workoutwizard.ui.theme.WorkoutWizardTheme

@Composable
fun InitialUserDataLayout(
    modifier: Modifier = Modifier,
    initialUserDataViewModel: InitialUserDataViewModel,
    onComplete: () -> Unit = {}
) {
    val uiState by initialUserDataViewModel.uiState.collectAsState()

    var stageNumber by remember { mutableIntStateOf(11) }
    val stage = InitialUserDataStage.entries[stageNumber]

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .matchParentSize()
                .padding(dimensionResource(id = R.dimen.container_padding))
        ) {
            InitialUserDataProgressBar(
                stage = stage,
                modifier = Modifier
                    .weight(1F)
            )
            InitialUserDataContent(
                stage = stage,
                modifier = Modifier
                    .weight(18F)
                    .fillMaxWidth(),
                viewModel = initialUserDataViewModel,
                uiState = uiState
            )
            InitialUserDataButtons(
                stageNumber = stageNumber,
                onBackClick = { stageNumber-- },
                onNextClick = { stageNumber++ },
                onComplete = onComplete,
                uiState = uiState,
                modifier = Modifier
                    .weight(2F)
            )
        }
    }
}

@Composable
fun InitialUserDataContent(
    modifier: Modifier = Modifier,
    stage: InitialUserDataStage,
    viewModel: InitialUserDataViewModel,
    uiState: InitialUserDataUiState
) {

   Column(
       modifier = modifier
   ) {
       HeaderWithContent(
           headerText = stage.stageTitle,
           style = MaterialTheme.typography.headlineMedium
       ) {
           Text(text = stringResource(id = stage.stageText))
       }
       MainSpacer()
       when (stage) {
           InitialUserDataStage.USER_DATA_SEX -> InitialUserDataSelect(
               onChange = { viewModel.changeSex(it) },
               value = uiState.sex,
               options = Datasource.initDataSexOptions,
               labelText = R.string.init_data_header_sex
           )
           InitialUserDataStage.USER_DATA_AGE -> InitialUserDataInput(
               onChange = { viewModel.changeAge(it) },
               value = uiState.age,
               placeholder = "Alter eingeben",
           )
           InitialUserDataStage.USER_DATA_HEIGHT -> InitialUserDataInput(
               onChange = { viewModel.changeHeight(it) },
               value = uiState.height,
               placeholder = "Größe eingeben",
           )
           InitialUserDataStage.USER_DATA_WEIGHT -> InitialUserDataInput(
               onChange = { viewModel.changeWeight(it) },
               value = uiState.weight,
               placeholder = "Gewicht eingeben",
           )
           InitialUserDataStage.USER_DATA_SLEEP_HOURS -> InitialUserDataInput(
               onChange = { viewModel.changeSleepHours(it) },
               value = uiState.sleepHours,
               placeholder = "Schlafstunden eingeben"
           )
           InitialUserDataStage.USER_DATA_WORK_HOURS -> InitialUserDataInput(
               onChange = { viewModel.changeWorkHours(it) },
               value = uiState.workHours,
               placeholder = "Arbeitsstunden eingeben"
           )
           InitialUserDataStage.USER_DATA_WORK_TYPE -> InitialUserDataSelect(
               onChange = { viewModel.changeWorkType(it) },
               value = uiState.workType,
               options = Datasource.initDataWorkTypeOptions,
               labelText = R.string.init_data_header_work_type
           )
           InitialUserDataStage.USER_DATA_WORKOUT_HOURS -> InitialUserDataInput(
               onChange = { viewModel.changeWorkoutHours(it) },
               value = uiState.workoutHours,
               placeholder = "Sportstunden eingeben"
           )
           InitialUserDataStage.USER_DATA_GOAL -> InitialUserDataSelect(
               onChange = { viewModel.changeGoalType(it) },
               value = uiState.goal,
               labelText = R.string.init_data_header_goal,
               options = Datasource.initDataGoalOptions
           )
           InitialUserDataStage.USER_DATA_GOAL_SPEED -> InitialUserDataSelect(
               onChange = { viewModel.changeGoalSpeedType(it) },
               value = uiState.goalSpeed,
               options = Datasource.initDataGoalSpeedOptions,
               labelText = R.string.init_data_header_goal_speed
           )
           InitialUserDataStage.USER_DATA_OVERVIEW -> InitialUserDataOverview(
               uiState = uiState
           )
           else -> null
       }
   }
}

@Composable
fun InitialUserDataOverview(
    modifier: Modifier = Modifier,
    uiState: InitialUserDataUiState
) {
   Column(
       modifier = modifier
           .verticalScroll(rememberScrollState())
           .fillMaxWidth()
   ) {
       if (!checkIfInitialDataCanComplete(uiState)) {
           Text(
               text = stringResource(id = R.string.init_data_missing_values),
               color = MaterialTheme.colorScheme.error
           )
           MainSpacer()
       }
       // Geschlecht
       Column(
           modifier = Modifier
               .padding(
                   bottom = dimensionResource(id = R.dimen.same_content_space)
               )
       ) {
           val text =
               if (uiState.sex == -1)
                   "Nicht angegeben"
               else Datasource.initDataSexOptions[uiState.sex]
           Text(
               text = stringResource(id = R.string.init_data_header_sex),
               style = MaterialTheme.typography.titleLarge
           )
           Text(
               text = text,
               modifier = Modifier
                   .padding(
                       start = dimensionResource(id = R.dimen.same_content_space)
                   )
           )
       }
       // Alter
       Column(
           modifier = Modifier
               .padding(
                   bottom = dimensionResource(id = R.dimen.same_content_space)
               )
       ) {
           val text = uiState.age.ifEmpty { "Nicht angegeben" }
           Text(
               text = stringResource(id = R.string.init_data_header_age),
               style = MaterialTheme.typography.titleLarge
           )
           Text(
               text = "$text Jahre",
               modifier = Modifier
                   .padding(
                       start = dimensionResource(id = R.dimen.same_content_space)
                   )
           )
       }
       // Größe
       Column(
           modifier = Modifier
               .padding(
                   bottom = dimensionResource(id = R.dimen.same_content_space)
               )
       ) {
           val text = uiState.height.ifEmpty { "Nicht angegeben" }
           Text(
               text = stringResource(id = R.string.init_data_header_height),
               style = MaterialTheme.typography.titleLarge
           )
           Text(
               text = "$text cm",
               modifier = Modifier
                   .padding(
                       start = dimensionResource(id = R.dimen.same_content_space)
                   )
           )
       }
       // Gewicht
       Column(
           modifier = Modifier
               .padding(
                   bottom = dimensionResource(id = R.dimen.same_content_space)
               )
       ) {
           val text = uiState.weight.ifEmpty { "Nicht angegeben" }
           Text(
               text = stringResource(id = R.string.init_data_header_weight),
               style = MaterialTheme.typography.titleLarge
           )
           Text(
               text = "$text kg",
               modifier = Modifier
                   .padding(
                       start = dimensionResource(id = R.dimen.same_content_space)
                   )
           )
       }
       // Schlafstunden
       Column(
           modifier = Modifier
               .padding(
                   bottom = dimensionResource(id = R.dimen.same_content_space)
               )
       ) {
           val text = uiState.sleepHours.ifEmpty { "Nicht angegeben" }
           Text(
               text = stringResource(id = R.string.init_data_header_sleep_hours),
               style = MaterialTheme.typography.titleLarge
           )
           Text(
               text = "$text Stunden",
               modifier = Modifier
                   .padding(
                       start = dimensionResource(id = R.dimen.same_content_space)
                   )
           )
       }
       // Arbeitsstunden
       Column(
           modifier = Modifier
               .padding(
                   bottom = dimensionResource(id = R.dimen.same_content_space)
               )
       ) {
           val text = uiState.workHours.ifEmpty { "Nicht angegeben" }
           Text(
               text = stringResource(id = R.string.init_data_header_work_hours),
               style = MaterialTheme.typography.titleLarge
           )
           Text(
               text = "$text Stunden",
               modifier = Modifier
                   .padding(
                       start = dimensionResource(id = R.dimen.same_content_space)
                   )
           )
       }
       // Art der Arbeit
       Column(
           modifier = Modifier
               .padding(
                   bottom = dimensionResource(id = R.dimen.same_content_space)
               )
       ) {
           val text = when (uiState.workType) {
               0 -> "sitzend, wenig körperliche Aktivität"
               1 -> "überwiegend sitzend, gehend und stehend"
               2 -> "überwiegend stehend und gehend"
               3 -> "anstrengende körperliche Arbeit"
               else -> "Nicht angegeben"
           }
           Text(
               text = stringResource(id = R.string.init_data_header_work_type),
               style = MaterialTheme.typography.titleLarge
           )
           Text(
               text = text,
               modifier = Modifier
                   .padding(
                       start = dimensionResource(id = R.dimen.same_content_space)
                   )
           )
       }
       // Sportstunden
       Column(
           modifier = Modifier
               .padding(
                   bottom = dimensionResource(id = R.dimen.same_content_space)
               )
       ) {
           val text = uiState.workoutHours.ifEmpty { "Nicht angegeben" }
           Text(
               text = stringResource(id = R.string.init_data_header_workout_hours),
               style = MaterialTheme.typography.titleLarge
           )
           Text(
               text = "$text Stunden",
               modifier = Modifier
                   .padding(
                       start = dimensionResource(id = R.dimen.same_content_space)
                   )
           )
       }
       // Ziel
       Column(
           modifier = Modifier
               .padding(
                   bottom = dimensionResource(id = R.dimen.same_content_space)
               )
       ) {
           val text = when (uiState.goal) {
               0 -> "Gewichtsabnahme"
               1 -> "Muskelaufbau"
               else -> "Nicht angegeben"
           }
           Text(
               text = stringResource(id = R.string.init_data_header_goal),
               style = MaterialTheme.typography.titleLarge
           )
           Text(
               text = text,
               modifier = Modifier
                   .padding(
                       start = dimensionResource(id = R.dimen.same_content_space)
                   )
           )
       }
       // Zielgeschwindigkeit
       Column(
           modifier = Modifier
               .padding(
                   bottom = dimensionResource(id = R.dimen.same_content_space)
               )
       ) {
           val text = when (uiState.goalSpeed) {
               0 -> "langsam"
               1 -> "normal"
               2 -> "schnell"
               else -> "Nicht angegeben"
           }
           Text(
               text = stringResource(id = R.string.init_data_header_goal_speed),
               style = MaterialTheme.typography.titleLarge
           )
           Text(
               text = text,
               modifier = Modifier
                   .padding(
                       start = dimensionResource(id = R.dimen.same_content_space)
                   )
           )
       }
   }
}

@Composable
fun InitialUserDataSelect(
    modifier: Modifier = Modifier,
    onChange: (String) -> Unit = {},
    value: Int,
    options: List<String>,
    @StringRes labelText: Int
) {
    val menuValue =
        if (value == -1)
            ""
        else options[value]

    SelectMenu(
        menuOptions = options,
        labelText = stringResource(id = labelText),
        modifier = modifier
            .fillMaxWidth(),
        onSelectChange = onChange,
        value = menuValue
    )
}
@Composable
fun InitialUserDataInput(
    modifier: Modifier = Modifier,
    onChange: (String) -> Unit = {},
    value: String,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Number
) {
    TextField(
        value = value,
        onValueChange = onChange,
        placeholder = {
              Text(
                  text = placeholder
              )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done
        ),
        modifier = modifier
            .fillMaxWidth()
    )
}

@Composable
fun InitialUserDataButtons(
    modifier: Modifier = Modifier,
    stageNumber: Int,
    onBackClick: () -> Unit = {},
    onNextClick: () -> Unit = {},
    onComplete: () -> Unit = {},
    uiState: InitialUserDataUiState
) {
    @StringRes val nextButtonText =
        if (stageNumber != InitialUserDataStage.entries.size - 1)
            R.string.init_data_button_next
        else R.string.init_data_button_complete

    val canNext =
        if (stageNumber == InitialUserDataStage.entries.size - 1)
            checkIfInitialDataCanComplete(uiState)
        else true

    val buttonShape = CutCornerShape(
        topStart = dimensionResource(id = R.dimen.default_cut_corner_radius),
        bottomEnd = dimensionResource(id = R.dimen.default_cut_corner_radius)
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.container_padding)),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextButton(
            buttonText = R.string.init_data_button_previous,
            shape = buttonShape,
            enabled = stageNumber > 0,
            onButtonClick = onBackClick
        )
        TextButton(
            buttonText = nextButtonText,
            shape = buttonShape,
            enabled = canNext,
            onButtonClick =
                if (stageNumber != InitialUserDataStage.entries.size - 1)
                    onNextClick
                else onComplete
        )
    }
}

@Composable
fun InitialUserDataProgressBar(
    modifier: Modifier = Modifier,
    stage: InitialUserDataStage
) {
    val stageNumber = InitialUserDataStage.entries.indexOf(stage) + 1

    Box(
        modifier = modifier
            .fillMaxWidth()
//            .padding(dimensionResource(id = R.dimen.container_padding))
    ) {
        LinearProgressIndicator(
            progress = stageNumber.toFloat() / InitialUserDataStage.entries.size.toFloat(),
            color = MaterialTheme.colorScheme.primaryContainer,
            trackColor = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = modifier
                .fillMaxWidth()
        )
    }

}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun InitialUserDataLayoutPreview() {
    WorkoutWizardTheme {
        InitialUserDataLayout(
            initialUserDataViewModel = InitialUserDataViewModel()
        )
    }
}