package com.example.workoutwizard.ui.components

import androidx.annotation.StringRes
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource

@Composable
fun TextButton(
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit = {},
    enabled: Boolean = true,
    @StringRes buttonText: Int,
    shape: Shape = ButtonDefaults.shape
) {
    Button(
        onClick = onButtonClick,
        enabled = enabled,
        shape = shape,
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = buttonText)
        )
    }
}