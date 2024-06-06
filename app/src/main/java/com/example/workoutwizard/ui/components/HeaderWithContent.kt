package com.example.workoutwizard.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.workoutwizard.R
import com.example.workoutwizard.ui.theme.WorkoutWizardTheme

@Composable
fun HeaderWithContent(
    modifier: Modifier = Modifier,
    @StringRes headerText: Int,
    style: TextStyle = MaterialTheme.typography.titleMedium,
    @DrawableRes headerIcon: Int? = null,
    @StringRes headerIconDescription: Int? = null,
    activeHeaderIconColor: Color = MaterialTheme.colorScheme.primaryContainer,
    expandedContent: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    val bottomPadding = if (headerIcon != null) R.dimen.zero_dp else R.dimen.same_content_space

    var expandedHeaderIcon by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(
                bottom = dimensionResource(id = bottomPadding)
            )
        ) {
            Text(
                text = stringResource(id = headerText),
                style = style,
            )
            if (headerIcon != null) {
                IconButton(
                    onClick = { expandedHeaderIcon = !expandedHeaderIcon }
                ) {
                    Icon(
                        painter = painterResource(id = headerIcon),
                        contentDescription = if (headerIconDescription != null) stringResource(id = headerIconDescription) else null,
                        tint = if (expandedHeaderIcon) activeHeaderIconColor else LocalContentColor.current
                    )
                }
            }
        }
        if (headerIcon != null) {
            AnimatedVisibility(
                visible = expandedHeaderIcon
            ) {
                Column {
                    expandedContent()
                    MainSpacer(
                        spaceSize = R.dimen.same_content_space
                    )
                }
            }
        }
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderWithContentPreview() {
    WorkoutWizardTheme {
        HeaderWithContent(
            headerText = R.string.workout_progress_header,
            headerIcon = R.drawable.info_24
        ) {
            
        }
    }
}