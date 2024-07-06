package com.example.workoutwizard

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.workoutwizard.helper.db.saveInitialData
import com.example.workoutwizard.helper.db.setCreatedInitialData
import com.example.workoutwizard.ui.AuthLayout
import com.example.workoutwizard.ui.CaloriesAdd
import com.example.workoutwizard.ui.CaloriesScreen
import com.example.workoutwizard.ui.InitialUserDataLayout
import com.example.workoutwizard.ui.OverviewScreen
import com.example.workoutwizard.ui.WorkoutAdd
import com.example.workoutwizard.ui.WorkoutAddScreen
import com.example.workoutwizard.ui.WorkoutEditScreen
import com.example.workoutwizard.ui.WorkoutPlanScreen
import com.example.workoutwizard.ui.WorkoutScreen
import com.example.workoutwizard.ui.WorkoutSection
import com.example.workoutwizard.ui.components.BottomBar
import com.example.workoutwizard.ui.model.AuthViewModel
import com.example.workoutwizard.ui.model.CaloriesViewModel
import com.example.workoutwizard.ui.model.InitialUserDataViewModel
import com.example.workoutwizard.ui.model.WorkoutViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun WorkoutWizardApp(
    modifier: Modifier = Modifier,
    auth: FirebaseAuth,
    db: FirebaseFirestore,
    authViewModel: AuthViewModel = viewModel(),
    initialUserDataViewModel: InitialUserDataViewModel = viewModel(),
    workoutViewModel: WorkoutViewModel = viewModel(),
    caloriesViewModel: CaloriesViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backstackEntry by navController.currentBackStackEntryAsState()
    val route = backstackEntry?.destination?.route

    val onMainNavigationChange: (MainNavigationType) -> Unit = {
        navController.navigate(it.name)
    }

    val onChangeAuthTypeClick: (AuthType) -> Unit = {
        navController.navigate(it.name)
    }
    
    val containerPadding = when (route) {
        AuthType.LOGIN.name -> dimensionResource(id = R.dimen.zero_dp)
        AuthType.REGISTER.name -> dimensionResource(id = R.dimen.zero_dp)
        InitialUserDataStage.USER_DATA_INTRO.name -> dimensionResource(id = R.dimen.zero_dp)
        else -> dimensionResource(id = R.dimen.container_padding)
    }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
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
                startDestination = AuthType.LOGIN.name
            ) {
                // Authentifizierung
                composable(
                    route = AuthType.LOGIN.name
                ) {
                    AuthLayout(
                        authViewModel = authViewModel,
                        onChangeAuthTypeClick = { onChangeAuthTypeClick(AuthType.REGISTER) },
                        onAuthButtonClick = {
                            authViewModel.loginButtonClick(
                                auth,
                                scope,
                                snackbarHostState,
                                navController,
                                db,
                            )
                        },
                        route = route
                    )
                }

                composable(
                    route = AuthType.REGISTER.name
                ) {
                    AuthLayout(
                        authViewModel = authViewModel,
                        onChangeAuthTypeClick = { onChangeAuthTypeClick(AuthType.LOGIN) },
                        onAuthButtonClick = { authViewModel.registerButtonClick(auth, scope, snackbarHostState, navController, db) },
                        route = route
                    )
                }

                composable(
                    route = InitialUserDataStage.USER_DATA_INTRO.name
                ) {
                    InitialUserDataLayout(
                        initialUserDataViewModel = initialUserDataViewModel
                    ) {
                        saveInitialData(
                            auth.currentUser!!.uid,
                            initialUserDataViewModel.uiState.value,
                            db
                        ) {
                            setCreatedInitialData(
                                auth.currentUser!!.uid,
                                db
                            )
                            navController.navigate(MainNavigationType.MAIN_OVERVIEW.name)
                        }
                    }
                }

                // Main Navigation
                composable(
                    route = MainNavigationType.MAIN_OVERVIEW.name
                ) {
                    OverviewScreen(
                        workoutViewModel = workoutViewModel,
                        caloriesViewModel = caloriesViewModel,
                        addWorkoutNavigation = { navController.navigate(SubNavigationType.SUB_WORKOUT_ADD.name) },
                        addCaloriesNavigation = { navController.navigate(SubNavigationType.SUB_CALORIES_ADD.name) },
                        editWorkoutNavigation = { navController.navigate("workout/edit/${it.workoutID}")}
                    )
                }
                composable(
                    route = MainNavigationType.MAIN_WORKOUT.name
                ) {
                    WorkoutScreen(
                        workoutViewModel = workoutViewModel,
                        addWorkoutNavigation = { navController.navigate(SubNavigationType.SUB_WORKOUT_ADD.name) },
                        planWorkoutNavigation = { navController.navigate(SubNavigationType.SUB_WORKOUT_PLAN.name) },
                        editWorkoutNavigation = { navController.navigate("workout/edit/${it.workoutID}")}
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
                    route = SubNavigationType.SUB_WORKOUT_PLAN.name
                ) {
                    WorkoutPlanScreen(
                        workoutViewModel = workoutViewModel
                    )
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
                        onWorkoutClick = {
                            navController.navigate("workout/add/${it.name}")
                        }
                    )
                }

                composable(
                    route = "workout/add/{workoutToAdd}",
                    arguments = listOf(
                        navArgument("workoutToAdd") {
                            type = NavType.StringType
                        }
                    )
                ) {
                    val workoutString = backstackEntry?.arguments?.getString("workoutToAdd") ?: ""
                    WorkoutAdd(
                        workoutString = workoutString,
                        workoutViewModel = workoutViewModel,
                        onAddClick = {
                            navController.navigate(MainNavigationType.MAIN_WORKOUT.name)
                        },
                        snackbarHostState = snackbarHostState,
                        scope = scope,
                        modifier = Modifier
                            .padding(innerPadding)
                    )
                }

                composable(
                    route = "workout/edit/{workoutToEdit}",
                    arguments = listOf(
                        navArgument("workoutToEdit") {
                            type = NavType.StringType
                        }
                    )
                ) {
                    val workoutID = backstackEntry?.arguments?.getString("workoutToEdit") ?: ""
                    WorkoutEditScreen(
                        workoutID = workoutID,
                        workoutViewModel = workoutViewModel,
                        onWorkoutChange = { navController.navigate(MainNavigationType.MAIN_OVERVIEW.name) }
                    )
                }

                composable(
                    route = MainNavigationType.MAIN_CALORIES.name
                ) {
                    CaloriesScreen(
                        caloriesViewModel = caloriesViewModel,
                        addCaloriesNavigation = {
                            navController.navigate(SubNavigationType.SUB_CALORIES_ADD.name)
                        }
                    )
                }

                composable(
                    route = SubNavigationType.SUB_CALORIES_ADD.name
                ) {
                    CaloriesAdd()
                }
            }
    }
}
