package com.example.workoutwizard.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.workoutwizard.data.MainNavigationType

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    items: List<MainNavigationType>,
    currentDestination: String = "MAIN_OVERVIEW",
    onMainNavigationChange: (MainNavigationType) -> Unit = {},
) {
    NavigationBar(
        modifier = modifier
    ) {
        items.forEach {
            item ->
                NavigationBarItem(
                    selected = item.name == currentDestination,
                    onClick = { onMainNavigationChange(item) },
                    icon = {
                           Icon(
                               painter = painterResource(id = item.mainIcon), 
                               contentDescription = stringResource(id = item.mainHeader)
                           )
                    },
                    label = {
                        Text(
                            text = stringResource(id = item.mainHeader)
                        )
                    }
                )
        }
    }
}

