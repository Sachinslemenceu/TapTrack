package com.slemenceu.taptrack

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.slemenceu.taptrack.mousepad.ui.home_screen.HomeScreen
import com.slemenceu.taptrack.authentication.ui.login_screen.LoginScreen
import com.slemenceu.taptrack.authentication.ui.login_screen.LoginViewModel
import com.slemenceu.taptrack.authentication.ui.register_screen.RegisterScreen
import com.slemenceu.taptrack.authentication.ui.register_screen.RegisterViewModel
import com.slemenceu.taptrack.authentication.ui.splash_screen.SplashScreen
import com.slemenceu.taptrack.authentication.ui.splash_screen.SplashViewModel
import com.slemenceu.taptrack.mousepad.ui.home_screen.HomeViewModel
import com.slemenceu.taptrack.mousepad.ui.mousepad_screen.MouseScreen
import com.slemenceu.taptrack.mousepad.ui.mousepad_screen.MouseViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val mouseViewModel = koinViewModel<MouseViewModel>()

    NavHost(
        navController = navController,
        startDestination = SplashScreen
    ) {

        composable<SplashScreen> {
            val viewModel = koinViewModel<SplashViewModel>()
            SplashScreen(
                modifier = modifier,
                uiState = viewModel.uiState.collectAsState().value,
                onEvent = viewModel::onEvent,
                uiEffect = viewModel.uiEffect,
                onGetStartedClicked = {
                    navController.navigate(LoginScreen)
                },
                onNavigateToHome = {
                    navController.navigate(HomeScreen){
                        popUpTo(SplashScreen) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<LoginScreen> {
            val viewModel = koinViewModel<LoginViewModel>()
            LoginScreen(
                modifier = modifier,
                uiState = viewModel.uiState.collectAsState().value,
                onEvent = viewModel::onEvent,
                uiEffect = viewModel.uiEffect,
                onNavigateToHome = {
                    navController.navigate(HomeScreen){
                        popUpTo(SplashScreen) { inclusive = true }
                        launchSingleTop = true
                    } },
                onNavigateToRegister = { navController.navigate(RegisterScreen) }
            )
        }
        composable<HomeScreen> {
            val viewModel = koinViewModel<HomeViewModel>()
            HomeScreen(
                uiState = viewModel.uiState.collectAsState().value,
                onEvent = viewModel::onEvent,
                uiEffect = viewModel.uiEffect,
                navigateToLogin = { navController.navigate(LoginScreen) },
                navigateToMousepad = { navController.navigate(MouseScreen) }
            )
        }
        composable<RegisterScreen> {
            val viewModel = koinViewModel< RegisterViewModel>()
            RegisterScreen(
                modifier = modifier,
                uiState = viewModel.uiState.collectAsState().value,
                onEvent = viewModel::onEvent,
                uiEffect = viewModel.uiEffect,
                onNavigateToHome = {
                    navController.navigate(HomeScreen){
                        popUpTo(SplashScreen) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<MouseScreen> {
            MouseScreen(
                onEvent = mouseViewModel::onEvent,
            )
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
@Serializable
object MouseScreen

