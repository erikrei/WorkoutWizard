package com.example.workoutwizard.data

enum class AuthType(val title: String) {
    LOGIN("Login"),
    REGISTER("Registrieren")
}

data class AuthUiState(
    val username: String = "",
    val password: String = "",
    val authType: AuthType = AuthType.LOGIN
)
