package com.example.workoutwizard.ui.model

import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.workoutwizard.data.AuthType
import com.example.workoutwizard.data.AuthUiState
import com.example.workoutwizard.data.InitialUserDataStage
import com.example.workoutwizard.data.MainNavigationType
import com.example.workoutwizard.helper.db.loginUser
import com.example.workoutwizard.helper.db.registerUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    val loginButtonClick: (FirebaseAuth, CoroutineScope, SnackbarHostState, NavHostController, FirebaseFirestore) -> Unit = {
            auth, scope, snackbarHostState, navController, db ->
                var toastMessage = ""
                try {
                    auth.signInWithEmailAndPassword(
                    uiState.value.email,
                    uiState.value.password
                ).addOnCompleteListener {
                        task ->
                    if (task.isSuccessful) {
                        val onSuccessLogin: (DocumentSnapshot) -> Unit = {
                            document ->
                                val hasInitialData = document.get("createdInitialData")
                                if (hasInitialData is Boolean && hasInitialData) {
                                    navController.navigate(MainNavigationType.MAIN_OVERVIEW.name)
                                } else navController.navigate(InitialUserDataStage.USER_DATA_INPUT.name)
                        }
                        loginUser(auth.uid!!, db, onSuccessLogin)
                    } else {
                        toastMessage = "Login fehlgeschlagen. Versuchen Sie es erneut."
                        toastFeedback(scope, snackbarHostState, toastMessage)
                    }
                }
        } catch(exception: IllegalArgumentException) {
            toastMessage = "Sie müssen ihre Daten eingeben."
            toastFeedback(scope, snackbarHostState, toastMessage)
        }
    }

    val registerButtonClick: (FirebaseAuth, CoroutineScope, SnackbarHostState, NavHostController, FirebaseFirestore) -> Unit = {
        auth, scope, snackbarHostState, navController, db ->
            var toastMessage = ""
            try {
                auth.createUserWithEmailAndPassword(
                    uiState.value.email,
                    uiState.value.password
                ).addOnCompleteListener {
                    task ->
                        if (task.isSuccessful && auth.currentUser != null) {
                            toastMessage = "Registrierung von ${uiState.value.email} erfolgreich. Sie können sich jetzt einloggen."
                            val onSuccessRegister: () -> Unit = { navController.navigate(AuthType.LOGIN.name) }
                            registerUser(auth.uid!!, uiState.value.email, db, onSuccessRegister)
                        } else {
                            toastMessage = "Registrierung fehlgeschlagen. Versuchen Sie es erneut."
                        }
                        toastFeedback(scope, snackbarHostState, toastMessage)
                }
            } catch(exception: IllegalArgumentException) {
                toastMessage = "Sie müssen ihre Daten eingeben."
                toastFeedback(scope, snackbarHostState, toastMessage)
            }
    }

    val toastFeedback: (CoroutineScope, SnackbarHostState, String) -> Unit = {
        scope, snackbarHostState, message ->
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = message
                )
            }
    }

    fun onChangeEmail(emailInput: String) {
        _uiState.update {
            currentState ->
                currentState.copy(
                    email = emailInput
                )
        }
    }

    fun onChangePassword(passwordInput: String) {
        _uiState.update {
            currentState ->
                currentState.copy(
                    password = passwordInput
                )
        }
    }


}