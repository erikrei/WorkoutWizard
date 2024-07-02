package com.example.workoutwizard.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.workoutwizard.R
import com.example.workoutwizard.data.AuthType
import com.example.workoutwizard.ui.components.ImageFillSizeAlpha
import com.example.workoutwizard.ui.components.InputField
import com.example.workoutwizard.ui.components.InputFieldPassword
import com.example.workoutwizard.ui.model.AuthViewModel

@Composable
fun AuthLayout(
    modifier: Modifier = Modifier,
    onChangeAuthTypeClick: () -> Unit = {},
    onAuthButtonClick: () -> Unit = {},
    authViewModel: AuthViewModel = viewModel(),
    route: String?
) {
    val authUiState by authViewModel.uiState.collectAsState()

    val authType =
        if (route != null && route == AuthType.LOGIN.name)
            AuthType.LOGIN
        else AuthType.REGISTER

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        ImageFillSizeAlpha(
            image = R.drawable.auth_background,
            modifier = Modifier.matchParentSize(),
            alpha = .1f
        )
        Box(
            modifier = Modifier
                .padding(top = 64.dp)
                .size(100.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .align(Alignment.TopCenter),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.icon_text),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.headlineLarge,
            )
        }
        AuthUserInput(
            modifier = Modifier
                .align(Alignment.Center),
            usernameValue = authUiState.email,
            passwordValue = authUiState.password,
            authType = authType,
            usernameValueChange = { authViewModel.onChangeEmail(it) },
            passwordValueChange = { authViewModel.onChangePassword(it) },
            onAuthButtonClick = onAuthButtonClick
        )
        AuthChange(
            authType = authType,
            onChangeAuthTypeClick = onChangeAuthTypeClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun AuthUserInput(
    usernameValue: String,
    passwordValue: String,
    usernameValueChange: (String) -> Unit,
    passwordValueChange: (String) -> Unit,
    authType: AuthType,
    onAuthButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val inputFieldModifier = Modifier
        .fillMaxWidth()
        .padding(
            top = 4.dp,
            bottom = 4.dp
        )

    val authTypeReadable = if (authType == AuthType.LOGIN) "Login" else "Registrieren"

    Column(
        modifier = modifier
            .padding(
                dimensionResource(id = R.dimen.auth_column_padding)
            )
    ) {
        Text(
            text =  authTypeReadable,
            style = MaterialTheme.typography.headlineMedium,
        )
        InputField(
            value = usernameValue,
            onValueChange = usernameValueChange,
            label = R.string.auth_username_label,
            modifier = inputFieldModifier
        )
        InputFieldPassword(
            value = passwordValue,
            onValueChange = passwordValueChange,
            label = R.string.auth_password_label,
            modifier = inputFieldModifier
        )
        Button(
            onClick = onAuthButtonClick,
            modifier = inputFieldModifier,
            shape = CutCornerShape(
                topStart = dimensionResource(id = R.dimen.auth_button_cut),
                bottomEnd = dimensionResource(id = R.dimen.auth_button_cut)
            )
        ) {
            Text(
                text = authTypeReadable,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun AuthChange(
    authType: AuthType,
    onChangeAuthTypeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    @StringRes val changeText = if (authType == AuthType.LOGIN) R.string.auth_change_login else R.string.auth_change_register
    @StringRes val changeButtonText = if (authType == AuthType.LOGIN) R.string.register_header else R.string.login_header

    Column(
        modifier = modifier
            .padding(
                dimensionResource(id = R.dimen.auth_column_padding)
            )
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = changeText)
        )
        Button(
            onClick = onChangeAuthTypeClick,
            shape = CutCornerShape(
                topStart = dimensionResource(id = R.dimen.auth_button_cut),
                bottomEnd = dimensionResource(id = R.dimen.auth_button_cut)
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary,
            ),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = changeButtonText),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun AuthLayoutPreview() {
//    WorkoutWizardTheme {
//        AuthLayout()
//    }
//}