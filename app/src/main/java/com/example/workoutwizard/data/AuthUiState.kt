package com.example.workoutwizard.data

enum class AuthType(val title: String) {
    LOGIN("Login"),
    REGISTER("Registrieren")
}

data class AuthUiState(
    val email: String = "",
    val password: String = "",
)
