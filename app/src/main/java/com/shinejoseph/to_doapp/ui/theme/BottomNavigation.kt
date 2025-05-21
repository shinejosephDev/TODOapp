package com.shinejoseph.to_doapp.ui.theme

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Filled.Home)
    object All : Screen("all", "All", Icons.Filled.List)
    object Profile : Screen("profile", "Profile", Icons.Filled.Person)
}

val navItems = listOf(
    Screen.Home,
    Screen.All,
    Screen.Profile
)

@Composable
fun AppBottomNavigation(
    currentScreen: String,
    onScreenSelected: (String) -> Unit
) {
    BottomNavigation {
        navItems.forEach { screen ->
            BottomNavigationItem(
                selected = currentScreen == screen.route,
                onClick = { onScreenSelected(screen.route) },
                icon = { Icon(screen.icon, contentDescription = screen.title) },
                label = { Text(screen.title) }
            )
        }
    }
}
