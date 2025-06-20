package com.slemenceu.taptrack

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.slemenceu.taptrack.authentication.ui.login_screen.LoginScreen
import com.slemenceu.taptrack.authentication.ui.login_screen.LoginViewModel
import com.slemenceu.taptrack.authentication.ui.register_screen.RegisterScreen
import com.slemenceu.taptrack.authentication.ui.register_screen.RegisterViewModel
import com.slemenceu.taptrack.authentication.ui.splash_screen.SplashScreen
import com.slemenceu.taptrack.authentication.ui.splash_screen.SplashViewModel
import com.slemenceu.taptrack.mousepad.ui.home_screen.HomeScreen
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
        startDestination = Splash,
    ) {
        composable<Splash>() {
            val viewModel = koinViewModel<SplashViewModel>()
            SplashScreen(
                modifier = modifier,
                uiState = viewModel.uiState.collectAsState().value,
                onEvent = viewModel::onEvent,
                uiEffect = viewModel.uiEffect,
                onGetStartedClicked = {
                    navController.navigate(Splash)
                },
                onNavigateToHome = {
                    navController.navigate(Home){
                        popUpTo(Splash) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<Login> {
            val viewModel = koinViewModel<LoginViewModel>()
            LoginScreen(
                modifier = modifier,
                uiState = viewModel.uiState.collectAsState().value,
                onEvent = viewModel::onEvent,
                uiEffect = viewModel.uiEffect,
                onNavigateToHome = {
                    navController.navigate(Home){
                        popUpTo(Splash) { inclusive = true }
                        launchSingleTop = true
                    } },
                onNavigateToRegister = { navController.navigate(Register) }
            )
        }
        composable<Home>(
            enterTransition = {
                slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Up,
                animationSpec = tween(500)
            ) +
                    fadeIn(animationSpec = tween(500))
            }
        ) {
            val viewModel = koinViewModel<HomeViewModel>()
            HomeScreen(
                uiState = viewModel.uiState.collectAsState().value,
                onEvent = viewModel::onEvent,
                uiEffect = viewModel.uiEffect,
                navigateToLogin = { navController.navigate(Login) },
                navigateToMousepad = { navController.navigate(Mouse) }
            )
        }
        composable<Register> {
            val viewModel = koinViewModel< RegisterViewModel>()
            RegisterScreen(
                modifier = modifier,
                uiState = viewModel.uiState.collectAsState().value,
                onEvent = viewModel::onEvent,
                uiEffect = viewModel.uiEffect,
                onNavigateToHome = {
                    navController.navigate(Home){
                        popUpTo(Splash) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<Mouse> {
            MouseScreen(
                onEvent = mouseViewModel::onEvent,
            )
        }
    }

}


@Serializable
data object Splash
@Serializable
data object Login
@Serializable
data object Register
@Serializable
data object Home
@Serializable
data object Mouse


