package com.example.workoutwizard.ui.model

import androidx.lifecycle.ViewModel
import com.example.workoutwizard.data.AuthType
import com.example.workoutwizard.data.AuthUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AuthViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun changeAuthType() {
        val authType = if (uiState.value.authType == AuthType.LOGIN) AuthType.REGISTER else AuthType.LOGIN
        _uiState.update {
            currentState ->
                currentState.copy(
                    authType = authType,
                    username = "",
                    password = ""
                )
        }
    }

    fun onChangeUsername(usernameInput: String) {
        _uiState.update {
            currentState ->
                currentState.copy(
                    username = usernameInput
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