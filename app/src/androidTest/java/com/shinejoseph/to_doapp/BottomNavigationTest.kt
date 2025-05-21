package com.shinejoseph.to_doapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.shinejoseph.to_doapp.view.MainActivity
import org.junit.Rule
import org.junit.Test

class BottomNavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testBottomNavigationBar_initialScreenIsHome() {
        // Verify that the "Home Screen" text is displayed
        composeTestRule.onNodeWithTag("Home Screen Content").assertIsDisplayed()
        // Verify that the "Home" tab label in the bottom navigation is selected
        composeTestRule.onNodeWithText("Home").assertIsSelected()
    }

    @Test
    fun testBottomNavigationBar_navigateToAllScreen() {
        // Click on the "All" tab in the bottom navigation
        composeTestRule.onNodeWithText("All").performClick()
        // Verify that the "All Screen" text is displayed
        composeTestRule.onNodeWithTag("All Screen Content").assertIsDisplayed()
        // Verify that the "All" tab label is selected
        composeTestRule.onNodeWithText("All").assertIsSelected()
        // Verify that "Home" is no longer selected
        composeTestRule.onNodeWithText("Home").assertIsNotSelected()
    }

    @Test
    fun testBottomNavigationBar_navigateToProfileScreen() {
        // Click on the "Profile" tab in the bottom navigation
        composeTestRule.onNodeWithText("Profile").performClick()
        // Verify that the "Profile Screen" text is displayed
        composeTestRule.onNodeWithTag("Profile Screen Content").assertIsDisplayed()
        // Verify that the "Profile" tab label is selected
        composeTestRule.onNodeWithText("Profile").assertIsSelected()
        // Verify that "All" is no longer selected
        composeTestRule.onNodeWithText("All").assertIsNotSelected()
    }
}
