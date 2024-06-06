package com.example.workoutwizard.ui.components

import androidx.annotation.DimenRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.workoutwizard.R

@Composable
fun MainSpacer(
    modifier: Modifier = Modifier,
    @DimenRes spaceSize: Int = R.dimen.spacer_padding
) {
    Spacer(
        modifier = modifier
            .padding(
                dimensionResource(id = spaceSize)
            )
    )
}