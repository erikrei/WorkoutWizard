package com.example.workoutwizard.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.workoutwizard.R
import com.example.workoutwizard.ui.components.CaloriesCard
import com.example.workoutwizard.ui.components.HeaderWithContent
import com.example.workoutwizard.ui.components.InputField
import com.example.workoutwizard.ui.components.MainSpacer
import com.example.workoutwizard.ui.components.NavigationHeader
import com.example.workoutwizard.ui.model.CaloriesViewModel
import com.example.workoutwizard.ui.theme.WorkoutWizardTheme

@Composable
fun CaloriesScreen(
    modifier: Modifier = Modifier,
    caloriesViewModel: CaloriesViewModel = viewModel(),
    addCaloriesNavigation: () -> Unit = {}
) {
    val uiState by caloriesViewModel.uiState.collectAsState()

    Column(
        modifier = modifier
    ) {
        NavigationHeader(
            headerText = R.string.main_calories_header,
            sideContent = {
                IconButton(
                    onClick = addCaloriesNavigation
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.add_48),
                        contentDescription = stringResource(id = R.string.calories_header_add_description)
                    )
                }
            }
        )
        MainSpacer()
        CaloriesCard(
            todayCaloriesTaken = uiState.todayCaloriesTaken,
            todayCaloriesLimit = uiState.todayCaloriesLimit,
            todayCaloriesBurned = uiState.todayCaloriesBurned,
            withoutAddButton = true
        )
    }
}

@Composable
fun CaloriesAdd(
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf("") }

   Column(
       modifier = modifier
   ) {
       NavigationHeader(
           headerText = R.string.calories_add
       )
       MainSpacer()
       HeaderWithContent(
           headerText = R.string.calories_add_name
       ) {
//            InputField(
//                value = name,
//                onValueChange = { name = it },
//                label =
//            )
       }
   }
}

@Preview(showSystemUi = true)
@Composable
fun CaloriesAddPreview() {
    WorkoutWizardTheme {
        CaloriesAdd()
    }
}

@Preview(showSystemUi = true)
@Composable
fun CaloriesScreenPreview() {
    WorkoutWizardTheme {
        CaloriesScreen()
    }
}