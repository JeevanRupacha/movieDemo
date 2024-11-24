package com.demo.moviedemo.core.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally


object NavigationUtils {

    /**
     * Creates and returns an enter transition for a composable.
     *
     * This transition combines a horizontal slide-in animation with a fade-in animation.
     *
     * @return An [EnterTransition] instance representing the combined animation.
     */
    fun enterTransition(): EnterTransition {
        return slideInHorizontally(
            initialOffsetX = { 300 },
            animationSpec = tween(
                durationMillis = 600,
                easing = FastOutSlowInEasing
            )
        ) + fadeIn(animationSpec = tween(600))
    }

    /**
     * Creates and returns an exit transition for a composable.
     *
     * This transition combines a horizontal slide-out animation with a fade-out animation.
     */
    fun exitTransition(): ExitTransition {
        return slideOutHorizontally(
            targetOffsetX = { -300 },
            animationSpec = tween(
                durationMillis = 600,
                easing = FastOutSlowInEasing
            )
        ) + fadeOut(animationSpec = tween(600))
    }

    /**
     * Creates and returns a pop enter transition for a composable.
     *
     * This transition combines a horizontal slide-in animation with a fade-in animation.
     */
    fun popEnterTransition(): EnterTransition {
        return slideInHorizontally(
            initialOffsetX = { -300 },
            animationSpec = tween(
                durationMillis = 600,
                easing = FastOutSlowInEasing
            )
        ) + fadeIn(animationSpec = tween(600))
    }

    /**
     * Creates and returns a pop exit transition for a composable.
     *
     * This transition combines a horizontal slide-out animation with a fade-out animation.
     */
    fun popExitTransition(): ExitTransition {
        return slideOutHorizontally(
            targetOffsetX = { 300 },
            animationSpec = tween(
                durationMillis = 600,
                easing = FastOutSlowInEasing
            )
        ) + fadeOut(animationSpec = tween(600))
    }
}