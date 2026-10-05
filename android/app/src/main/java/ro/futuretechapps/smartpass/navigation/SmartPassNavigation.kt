package ro.futuretechapps.smartpass.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ro.futuretechapps.smartpass.ui.screens.home.HomeRoute
import ro.futuretechapps.smartpass.ui.screens.login.LoginRoute
import ro.futuretechapps.smartpass.ui.screens.session.SessionRoute

@Composable
fun SmartPassNavigation() {

    val navController =
        rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SESSION
    ) {

        composable(Routes.SESSION) {

            SessionRoute(

                onAuthenticated = {

                    navController.navigate(
                        Routes.HOME
                    ) {

                        popUpTo(
                            Routes.SESSION
                        ) {
                            inclusive = true
                        }
                    }
                },

                onUnauthenticated = {

                    navController.navigate(
                        Routes.LOGIN
                    ) {

                        popUpTo(
                            Routes.SESSION
                        ) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Routes.LOGIN) {

            LoginRoute(
                onLoginSuccess = {

                    navController.navigate(
                        Routes.HOME
                    ) {

                        popUpTo(
                            Routes.LOGIN
                        ) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Routes.HOME) {

            HomeRoute(
                onLogoutSuccess = {

                    navController.navigate(
                        Routes.LOGIN
                    ) {

                        popUpTo(
                            Routes.HOME
                        ) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}