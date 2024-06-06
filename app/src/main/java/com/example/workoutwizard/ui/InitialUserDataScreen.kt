package com.example.workoutwizard.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.workoutwizard.R
import com.example.workoutwizard.data.InitialUserDataStage
import com.example.workoutwizard.data.InitialUserDataUiState
import com.example.workoutwizard.ui.components.ImageFillSizeAlpha
import com.example.workoutwizard.ui.components.InputField
import com.example.workoutwizard.ui.components.NavigationButton
import com.example.workoutwizard.ui.model.InitialUserDataViewModel
import com.example.workoutwizard.ui.theme.WorkoutWizardTheme

@Composable
fun InitialUserDataLayout(
    modifier: Modifier = Modifier,
    initialUserDataViewModel: InitialUserDataViewModel = viewModel(),
    stage: InitialUserDataStage = InitialUserDataStage.USER_DATA_INPUT,
    onNextClick: () -> Unit = {},
    onBackDataInputClick: () -> Unit = {}
) {
    val initialUserDataUiState by initialUserDataViewModel.uiState.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        ImageFillSizeAlpha(
            image = R.drawable.initial_user_data_background,
            modifier = Modifier
                .matchParentSize(),
            alpha = .1f
        )
        Column(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.container_padding))
        ) {
            InitialUserDataHeader(
                stage = stage
            )
            InitialUserDataMainContent(
                stage = stage,
                initialUserDataViewModel = initialUserDataViewModel,
                initialUserDataUiState = initialUserDataUiState,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
        InitialUserDataButtonContainer(
            stage = stage,
            initialUserDataUiState = initialUserDataUiState,
            onNextClick = onNextClick,
            onBackDataInputClick = onBackDataInputClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(dimensionResource(id = R.dimen.container_padding))
                .fillMaxWidth()
        )
    }
}

@Composable
fun InitialUserDataMainContent(
    modifier: Modifier = Modifier,
    stage: InitialUserDataStage,
    initialUserDataViewModel: InitialUserDataViewModel,
    initialUserDataUiState: InitialUserDataUiState
) {
    if (stage == InitialUserDataStage.USER_DATA_INPUT) {
        InitialUserDataInputs(
            modifier = modifier,
            initialUserDataViewModel = initialUserDataViewModel,
            initialUserDataUiState = initialUserDataUiState
        )
    } else {
        Column(
            modifier = modifier
        ) {
            InitialUserDataContent(
                contentType = "Alter",
                contentText = initialUserDataUiState.age
            )
            InitialUserDataContent(
                contentType = "Größe",
                contentText = "${initialUserDataUiState.height} cm"
            )
            InitialUserDataContent(
                contentType = "Gewicht",
                contentText = "${initialUserDataUiState.weight} kg"
            )
        }
    }
}

@Composable
fun InitialUserDataContent(
    modifier: Modifier = Modifier,
    contentType: String,
    contentText: String
) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.secondary)
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = contentType,
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = contentText,
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun InitialUserDataInputs(
    modifier: Modifier = Modifier,
    initialUserDataViewModel: InitialUserDataViewModel,
    initialUserDataUiState: InitialUserDataUiState
) {
    val inputModifier = Modifier.fillMaxWidth()

    Column(
        modifier = modifier
    ) {
        InputField(
            value = initialUserDataUiState.age,
            onValueChange = { initialUserDataViewModel.changeAge(it) },
            label = R.string.init_data_age,
            keyboardType = KeyboardType.Number,
            modifier = inputModifier
        )
        InputField(
            value = initialUserDataUiState.height,
            onValueChange = { initialUserDataViewModel.changeHeight(it) },
            label = R.string.init_data_height,
            keyboardType = KeyboardType.Number,
            modifier = inputModifier
        )
        InputField(
            value = initialUserDataUiState.weight,
            onValueChange = { initialUserDataViewModel.changeWeight(it) },
            label = R.string.init_data_weight,
            keyboardType = KeyboardType.Number,
            modifier = inputModifier,
            isLast = true
        )
    }
}

@Composable
fun InitialUserDataHeader(
    stage: InitialUserDataStage,
    modifier: Modifier = Modifier
) {
    @StringRes val stageHeaderText =
        if (stage == InitialUserDataStage.USER_DATA_INPUT) R.string.init_data_input_header
        else R.string.init_data_overview_header

    Text(
        text = stringResource(id = stageHeaderText),
        style = MaterialTheme.typography.headlineLarge,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier
    )
}

@Composable
fun InitialUserDataButtonContainer(
    modifier: Modifier = Modifier,
    stage: InitialUserDataStage,
    initialUserDataUiState: InitialUserDataUiState,
    onNextClick: () -> Unit = {},
    onBackDataInputClick: () -> Unit = {}
) {
    val buttonShape = CutCornerShape(
        topStart = 8.dp,
        bottomEnd = 8.dp
    )

    if (stage == InitialUserDataStage.USER_DATA_INPUT) {
        NavigationButton(
            onNavigationClick = onNextClick,
            buttonText = R.string.init_data_next_button,
            buttonShape = buttonShape,
            enabled = initialUserDataUiState.age != "" &&
                      initialUserDataUiState.height != "" &&
                      initialUserDataUiState.weight != "",
            bold = true,
            modifier = modifier
        )
    } else {
        val buttonModifier = Modifier.fillMaxWidth()

        Column(
            modifier = modifier
        ) {
            NavigationButton(
                onNavigationClick = onBackDataInputClick,
                buttonText = R.string.init_data_back_button,
                buttonShape = buttonShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onError,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                ),
                modifier = buttonModifier
            )
            NavigationButton(
                onNavigationClick = onNextClick,
                buttonText = R.string.init_data_confirm_button,
                buttonShape = buttonShape,
                bold = true,
                modifier = buttonModifier
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun InitialUserDataLayoutPreview() {
    WorkoutWizardTheme {
        InitialUserDataLayout()
    }
}