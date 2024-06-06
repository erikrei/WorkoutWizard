package com.example.workoutwizard.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

@Composable
fun ImageFillSizeAlpha(
    @DrawableRes image: Int,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    alpha: Float = 1f
) {
    Image(
        painter = painterResource(id = image),
        contentDescription = null,
        contentScale = contentScale,
        modifier = modifier,
        alpha = alpha
    )
}