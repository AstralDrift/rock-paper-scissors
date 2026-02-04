package com.skr.game.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.skr.game.ui.screens.GameScreen
import com.skr.game.ui.screens.HomeScreen
import com.skr.game.ui.screens.ProfileScreen
import com.skr.game.ui.screens.RulesScreen
import com.skr.game.ui.screens.StakeScreen

object Routes {
    const val HOME = "home"
    const val RULES = "rules"
    const val STAKE = "stake"
    const val GAME = "game"
    const val PROFILE = "profile"
}

@Composable
fun SKRNavGraph(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                onPlay = { navController.navigate(Routes.STAKE) },
                onRules = { navController.navigate(Routes.RULES) },
                onProfile = { navController.navigate(Routes.PROFILE) },
            )
        }
        composable(Routes.RULES) {
            RulesScreen(onBack = { navController.popBackStack() })
        }
        composable(Routes.STAKE) {
            StakeScreen(
                onBack = { navController.popBackStack() },
                onMatchmaking = { navController.navigate(Routes.GAME) },
            )
        }
        composable(Routes.GAME) {
            GameScreen(
                onBack = { navController.popBackStack() },
                onMatchEnd = { navController.popBackStack(Routes.HOME, inclusive = false) },
            )
        }
        composable(Routes.PROFILE) {
            ProfileScreen(onBack = { navController.popBackStack() })
        }
    }
}
