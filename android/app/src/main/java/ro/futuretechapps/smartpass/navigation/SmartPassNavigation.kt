package ro.futuretechapps.smartpass.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ro.futuretechapps.smartpass.ui.screens.home.HomeScreen
import ro.futuretechapps.smartpass.ui.screens.login.LoginRoute

@Composable
fun SmartPassNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {

        composable(Routes.LOGIN) {

            LoginRoute(
                onLoginSuccess = {

                    navController.navigate(Routes.HOME) {

                        popUpTo(Routes.LOGIN) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Routes.HOME) {
            HomeScreen()
        }
    }
}