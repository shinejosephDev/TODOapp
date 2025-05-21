package com.shinejoseph.to_doapp.ui.theme

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun MainAppScreen() {
    val currentScreenRoute = remember { mutableStateOf(Screen.Home.route) }

    Scaffold(
        bottomBar = {
            AppBottomNavigation(
                currentScreen = currentScreenRoute.value,
                onScreenSelected = { route ->
                    currentScreenRoute.value = route
                }
            )
        }
    ) { innerPadding ->
        // Apply padding to the content area
        val modifier = Modifier.padding(innerPadding)

        when (currentScreenRoute.value) {
            Screen.Home.route -> HomeScreen() // Consider passing modifier if HomeScreen needs it
            Screen.All.route -> AllScreen()   // Consider passing modifier if AllScreen needs it
            Screen.Profile.route -> ProfileScreen() // Consider passing modifier if ProfileScreen needs it
        }
    }
}
