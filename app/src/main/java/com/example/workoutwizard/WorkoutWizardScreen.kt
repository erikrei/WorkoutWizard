package com.example.workoutwizard

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.workoutwizard.data.AuthType
import com.example.workoutwizard.data.Datasource.forbiddenDestination
import com.example.workoutwizard.data.Datasource.mainNavigationItems
import com.example.workoutwizard.data.InitialUserDataStage
import com.example.workoutwizard.data.MainNavigationType
import com.example.workoutwizard.data.SubNavigationType
import com.example.workoutwizard.ui.AuthLayout
import com.example.workoutwizard.ui.InitialUserDataLayout
import com.example.workoutwizard.ui.OverviewScreen
import com.example.workoutwizard.ui.WorkoutAddScreen
import com.example.workoutwizard.ui.WorkoutHistoryScreen
import com.example.workoutwizard.ui.WorkoutScreen
import com.example.workoutwizard.ui.WorkoutSection
import com.example.workoutwizard.ui.components.BottomBar
import com.example.workoutwizard.ui.model.AuthViewModel
import com.example.workoutwizard.ui.model.CaloriesViewModel
import com.example.workoutwizard.ui.model.InitialUserDataViewModel
import com.example.workoutwizard.ui.model.WorkoutViewModel

@Composable
fun WorkoutWizardApp(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = viewModel(),
    initialUserDataViewModel: InitialUserDataViewModel = viewModel(),
    workoutViewModel: WorkoutViewModel = viewModel(),
    caloriesViewModel: CaloriesViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backstackEntry by navController.currentBackStackEntryAsState()

    val onMainNavigationChange: (MainNavigationType) -> Unit = {
        navController.navigate(it.name)
    }

    val onChangeAuthTypeClick: (AuthType) -> Unit = {
        navController.navigate(it.name)
        authViewModel.changeAuthType()
    }

    val containerPadding = dimensionResource(id = R.dimen.container_padding)

    Scaffold(
        bottomBar = {
            val currentRoute = backstackEntry?.destination?.route ?: ""
            if (!forbiddenDestination.contains(currentRoute))
                BottomBar(
                    items = mainNavigationItems,
                    currentDestination = currentRoute,
                    onMainNavigationChange = { item -> onMainNavigationChange(item) }
                )
        }
    ) {
        innerPadding ->
            NavHost(
                modifier = modifier
                    .padding(
                        PaddingValues(
                            top = innerPadding.calculateTopPadding() + containerPadding,
                            bottom = innerPadding.calculateBottomPadding() + containerPadding,
                            start = containerPadding,
                            end = containerPadding
                        )
                    ),
                navController = navController,
//                startDestination = AuthType.LOGIN.name,
                startDestination = MainNavigationType.MAIN_OVERVIEW.name
            ) {

                // Authentifizierung
                composable(
                    route = AuthType.LOGIN.name
                ) {
                    AuthLayout(
                        authViewModel = authViewModel,
                        onChangeAuthTypeClick = { onChangeAuthTypeClick(AuthType.REGISTER) },
                        onSuccessLoginClick = { navController.navigate(InitialUserDataStage.USER_DATA_INPUT.name) },
                    )
                }
                composable(
                    route = AuthType.REGISTER.name
                ) {
                    AuthLayout(
                        authViewModel = authViewModel,
                        onChangeAuthTypeClick = { onChangeAuthTypeClick(AuthType.LOGIN) },
                    )
                }

                // Initial Benutzerdaten
                composable(
                    route = InitialUserDataStage.USER_DATA_INPUT.name
                ) {
                    InitialUserDataLayout(
                        stage = InitialUserDataStage.USER_DATA_INPUT,
                        initialUserDataViewModel = initialUserDataViewModel,
                        onNextClick = { navController.navigate(InitialUserDataStage.USER_DATA_OVERVIEW.name) }
                    )
                }
                composable(
                    route = InitialUserDataStage.USER_DATA_OVERVIEW.name
                ) {
                    InitialUserDataLayout(
                        stage = InitialUserDataStage.USER_DATA_OVERVIEW,
                        initialUserDataViewModel = initialUserDataViewModel,
                        onBackDataInputClick = { navController.navigate(InitialUserDataStage.USER_DATA_INPUT.name) },
                        onNextClick = { navController.navigate(MainNavigationType.MAIN_OVERVIEW.name) }
                    )
                }

                // Main Navigation
                composable(
                    route = MainNavigationType.MAIN_OVERVIEW.name
                ) {
                    OverviewScreen(
                        workoutViewModel = workoutViewModel,
                        caloriesViewModel = caloriesViewModel,
                        addWorkoutNavigation = { navController.navigate(SubNavigationType.SUB_WORKOUT_ADD.name) }
                    )
                }
                composable(
                    route = MainNavigationType.MAIN_WORKOUT.name
                ) {
                    WorkoutScreen(
                        workoutViewModel = workoutViewModel,
                        addWorkoutNavigation = { navController.navigate(SubNavigationType.SUB_WORKOUT_ADD.name) },
                        historyWorkoutNavigation = { navController.navigate(SubNavigationType.SUB_WORKOUT_HISTORY.name) }
                    )
                }
                composable(
                    route = MainNavigationType.MAIN_PLAN.name
                ) {

                }
                composable(
                    route = MainNavigationType.MAIN_CALORIES.name
                ) {

                }

                // Sub Navigation
                composable(
                    route = SubNavigationType.SUB_WORKOUT_ADD.name
                ) {
                    WorkoutAddScreen(
                        navigateToSection = {
                            navController.navigate("workout/add/section/${it.sectionName}")
                        }
                    )
                }

                composable(
                    route = SubNavigationType.SUB_WORKOUT_HISTORY.name
                ) {
                    WorkoutHistoryScreen()
                }





                composable(
                    route = "workout/add/section/{sectionName}",
                    arguments = listOf(
                        navArgument("sectionName") {
                            type = NavType.IntType
                        }
                    )
                ) {
                    val sectionName = backstackEntry?.arguments?.getInt("sectionName") ?: -1
                    WorkoutSection(
                        sectionName = sectionName,
                        workoutViewModel = workoutViewModel,
                        navigateToWorkoutScreen = { navController.navigate(MainNavigationType.MAIN_WORKOUT.name) }
                    )
                }
            }
    }
}
