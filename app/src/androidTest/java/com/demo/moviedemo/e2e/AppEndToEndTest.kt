package com.demo.moviedemo.e2e

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.demo.moviedemo.BuildScreen
import com.demo.moviedemo.MainActivity
import com.demo.moviedemo.core.theme.MovieDemoTheme
import com.demo.moviedemo.core.utils.TestTag
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AppEndToEndTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: NavHostController

    @Before
    fun setUp() {
        composeRule.activity.setContent {
            MovieDemoTheme {
                navController = rememberNavController()
                BuildScreen(
                    navController = navController,
                )
            }
        }
    }

    @Test
    fun app_travel_full_end_to_end_test() {
        // if Movie List title is visible or not when first time app launched
        composeRule.onNodeWithText("Movie List").assertIsDisplayed()

        // Wait until at least one item is displayed in the grid
        composeRule.waitUntil(timeoutMillis = 10_000) {
            composeRule.onAllNodesWithContentDescription(TestTag.MOVIE_GRID).fetchSemanticsNodes().isNotEmpty()
        }

        // Find the first item and perform a click
        composeRule.onAllNodesWithContentDescription(TestTag.MOVIE_GRID)[0].performClick()

        // Wait until movie details data is fetched from api
        composeRule.waitUntil(timeoutMillis = 10_000) {
            composeRule.onNodeWithTag(TestTag.MOVIE_DETAIL).isDisplayed()
        }

        // check back button exist
        composeRule.onNodeWithContentDescription("Back").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Back").performClick()

        //back to movie list screen with title Movie List
        composeRule.onNodeWithText("Movie List")
    }
}