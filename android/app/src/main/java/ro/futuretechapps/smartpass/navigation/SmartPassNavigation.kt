package ro.futuretechapps.smartpass.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ro.futuretechapps.smartpass.ui.screens.home.HomeScreen
import ro.futuretechapps.smartpass.ui.screens.login.LoginScreen

@Composable
fun SmartPassNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {

        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginClick = {
                    navController.navigate(Routes.HOME)
                }
            )
        }

        composable(Routes.HOME) {
            HomeScreen()
        }
    }
}