package com.shinejoseph.to_doapp.ui.theme

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@Composable
fun HomeScreen() {
    Text("Home Screen", Modifier.testTag("Home Screen Content"))
}

@Composable
fun AllScreen() {
    Text("All Screen", Modifier.testTag("All Screen Content"))
}

@Composable
fun ProfileScreen() {
    Text("Profile Screen", Modifier.testTag("Profile Screen Content"))
}
