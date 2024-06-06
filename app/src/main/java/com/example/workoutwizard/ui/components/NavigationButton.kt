package com.example.workoutwizard.ui.components

import androidx.annotation.StringRes
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight

@Composable
fun NavigationButton(
    modifier: Modifier = Modifier,
    onNavigationClick: () -> Unit = {},
    @StringRes buttonText: Int,
    buttonShape: Shape = ButtonDefaults.shape,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    bold: Boolean = false,
    enabled: Boolean = true
) {
    val fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal

    Button(
        onClick = onNavigationClick,
        shape = buttonShape,
        colors = colors,
        enabled = enabled,
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = buttonText),
            fontWeight = fontWeight
        )
    }
}