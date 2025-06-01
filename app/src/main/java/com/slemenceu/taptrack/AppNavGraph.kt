package com.slemenceu.taptrack

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.slemenceu.taptrack.authentication.ui.HomeScreen
import com.slemenceu.taptrack.authentication.ui.login_screen.LoginScreen
import com.slemenceu.taptrack.authentication.ui.register_screen.RegisterScreen
import com.slemenceu.taptrack.authentication.ui.splash_screen.SplashScreen
import kotlinx.serialization.Serializable

@Composable
fun AppNavGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = SplashScreen
    ) {
        composable<SplashScreen> {
            SplashScreen(
                modifier = modifier,
                onGetStartedClicked = {
                    navController.navigate(LoginScreen)
                },
                onNavigateToHome = {
                    navController.navigate(HomeScreen)
                }
            )
        }
        composable<LoginScreen> {
            LoginScreen(
                modifier = modifier,
                onNavigateToHome = { navController.navigate(HomeScreen) },
                onNavigateToRegister = { navController.navigate(RegisterScreen) }
            )
        }
        composable<HomeScreen> {
            HomeScreen(modifier = modifier)
        }
        composable<RegisterScreen> {
            RegisterScreen(modifier = modifier, onNavigateToHome = {navController.navigate(HomeScreen)})
        }
    }

}

@Serializable
object SplashScreen
@Serializable
object LoginScreen
@Serializable
object HomeScreen
@Serializable
object RegisterScreen

