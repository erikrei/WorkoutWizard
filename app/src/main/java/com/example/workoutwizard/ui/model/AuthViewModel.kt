package com.example.workoutwizard.ui.model

import android.util.Log
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.workoutwizard.data.AuthType
import com.example.workoutwizard.data.AuthUiState
import com.example.workoutwizard.data.InitialUserDataStage
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    val loginButtonClick: (FirebaseAuth, CoroutineScope, SnackbarHostState, NavHostController) -> Unit = {
            auth, scope, snackbarHostState, navController ->
                var toastMessage = ""
                try {
                    auth.signInWithEmailAndPassword(
                    uiState.value.email,
                    uiState.value.password
                ).addOnCompleteListener {
                        task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        Log.i("AuthViewModel", user.toString())
                        navController.navigate(InitialUserDataStage.USER_DATA_INPUT.name)
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

    val registerButtonClick: (FirebaseAuth, CoroutineScope, SnackbarHostState, NavHostController) -> Unit = {
        auth, scope, snackbarHostState, navController ->
            var toastMessage = ""
            try {
                auth.createUserWithEmailAndPassword(
                    uiState.value.email,
                    uiState.value.password
                ).addOnCompleteListener {
                    task ->
                        if (task.isSuccessful) {
                            toastMessage = "Registrierung von ${uiState.value.email} erfolgreich. Sie können sich jetzt einloggen."
                            navController.navigate(AuthType.LOGIN.name)
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