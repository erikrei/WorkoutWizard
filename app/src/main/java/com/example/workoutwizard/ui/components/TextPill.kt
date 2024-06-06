package com.example.workoutwizard.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.workoutwizard.ui.theme.WorkoutWizardTheme

@Composable
fun TextPill(
    modifier: Modifier = Modifier,
    text: String,
    backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer,
    textColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    textStyle: TextStyle = MaterialTheme.typography.labelLarge,
    fontWeight: FontWeight = FontWeight.Normal,
    pillRadius: Dp = 12.dp
) {
    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(pillRadius)
            )
    ) {
        Text(
            text = text,
            style = textStyle,
            color = textColor,
            fontWeight = fontWeight,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(
                    horizontal = pillRadius,
                    vertical = 2.dp
                ),
        )
    }
}

@Preview
@Composable
fun TextPillPreview() {
    WorkoutWizardTheme {
        TextPill(
            text = "Cardio",
        )
    }
}