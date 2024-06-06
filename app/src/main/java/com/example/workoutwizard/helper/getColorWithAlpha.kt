package com.example.workoutwizard.helper

import androidx.annotation.FloatRange
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils
import kotlin.math.floor

fun getColorWithAlpha(color: Color, @FloatRange(from = 0.0, to = 1.0) alpha: Float): Color {
    if (alpha < 0.0 || alpha > 1.0) {
        throw IllegalArgumentException("alpha muss zwischen 0.0 und 1.0 sein")
    }

    val alphaToInt = floor(0xFF * alpha).toInt()

    val colorWithAlpha = ColorUtils.setAlphaComponent(color.toArgb(), alphaToInt)

    return Color(colorWithAlpha)
}