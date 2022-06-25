package com.fiz.wisecrypto.ui.screens.main.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.fiz.wisecrypto.ui.screens.main.NamesMainScreen
import com.fiz.wisecrypto.ui.theme.hint

@Composable
fun MainBottomBar(
    navController: NavHostController
) {

    val backstackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backstackEntry?.destination

    val items =
        listOf(NamesMainScreen.Home, NamesMainScreen.Market, NamesMainScreen.Profile)

    val selectedItem = items.indexOfFirst { currentDestination?.route?.contains(it.name) ?: false }

    NavigationBar(
        modifier = Modifier,
        containerColor = MaterialTheme.colorScheme.onPrimary,
        contentColor = MaterialTheme.colorScheme.primary
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = items[index].iconId),
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = item.textId),
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                selected = currentDestination?.route?.contains(item.name) ?: false,
                onClick = {
                    if (selectedItem != index) {
                        navController.navigate(item.name) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.surface,
                    unselectedIconColor = MaterialTheme.colorScheme.hint,
                    unselectedTextColor = MaterialTheme.colorScheme.hint,
                )
            )
        }
    }
}