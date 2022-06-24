package com.fiz.wisecrypto.ui.screens.main.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.fiz.wisecrypto.R
import com.fiz.wisecrypto.ui.screens.main.NamesMainScreen
import com.fiz.wisecrypto.ui.theme.hint

@Composable
fun MainBottomBar(
    navController: NavHostController
) {

    var selectedItem by remember { mutableStateOf(0) }
    val items =
        listOf(NamesMainScreen.Home.name, NamesMainScreen.Market.name, NamesMainScreen.Profile.name)
    val icons =
        listOf(
            R.drawable.bottom_nav_ic_home,
            R.drawable.bottom_nav_ic_market,
            R.drawable.bottom_nav_ic_profile
        )

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
                        painter = painterResource(id = icons[index]),
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = item,
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                selected = selectedItem == index,
                onClick = {
                    if (selectedItem != index) {
                        selectedItem = index
                        navController.navigate(item) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
//                    indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 1f),
                    unselectedIconColor = MaterialTheme.colorScheme.hint,
                    unselectedTextColor = MaterialTheme.colorScheme.hint,
                )
            )
        }
    }
}