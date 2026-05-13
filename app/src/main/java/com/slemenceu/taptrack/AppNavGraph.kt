package com.slemenceu.taptrack

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController

import com.slemenceu.taptrack.features.authentication.ui.login_screen.LoginScreen
import com.slemenceu.taptrack.features.authentication.ui.login_screen.LoginViewModel
import com.slemenceu.taptrack.features.authentication.ui.register_screen.RegisterScreen
import com.slemenceu.taptrack.features.authentication.ui.register_screen.RegisterViewModel
import com.slemenceu.taptrack.features.authentication.ui.reset_password.ResetPasswordScreen
import com.slemenceu.taptrack.features.authentication.ui.reset_password.ResetPasswordViewModel
import com.slemenceu.taptrack.features.authentication.ui.splash_screen.SplashScreen
import com.slemenceu.taptrack.features.authentication.ui.splash_screen.SplashViewModel
import com.slemenceu.taptrack.features.mousepad.ui.home_screen.HomeScreen
import com.slemenceu.taptrack.features.mousepad.ui.home_screen.HomeViewModel
import com.slemenceu.taptrack.features.connection.ui.manual_connection.ManualConnectionScreen
import com.slemenceu.taptrack.features.connection.ui.scanner.ScannerScreen
import com.slemenceu.taptrack.features.connection.ui.scanner.ScannerViewModel
import com.slemenceu.taptrack.features.mousepad.ui.mousepad_screen.MouseScreen
import com.slemenceu.taptrack.features.mousepad.ui.mousepad_screen.MouseViewModel
import com.slemenceu.taptrack.features.mousepad.ui.options_screen.OptionsScreen
import com.slemenceu.taptrack.features.mousepad.ui.options_screen.OptionsViewModel
import com.slemenceu.taptrack.features.mousepad.ui.pc_guide_screen.PcGuideScreen
import com.slemenceu.taptrack.ui.theme.darkBlue900
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val mouseViewModel = koinViewModel<MouseViewModel>()

    Scaffold(
        containerColor = darkBlue900
    ) {
        NavHost(
            navController = navController,
            startDestination = AuthGraph,
            modifier = Modifier
                .padding(it)
        ) {

            // Authentication flow

            navigation<AuthGraph>(
                startDestination = Splash
            ) {
                composable<Splash>() {
                    val viewModel = koinViewModel<SplashViewModel>()
                    SplashScreen(
                        modifier = modifier,
                        uiState = viewModel.uiState.collectAsState().value,
                        onEvent = viewModel::onEvent,
                        uiEffect = viewModel.uiEffect,
                        onNavigateToLogin = {
                            Log.d("AppNavGraph", "AppNavGraph: onGetStartedClicked")
                            navController.navigate(Login) {
                                popUpTo(Splash) { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                        onNavigateToRegister = {
                            navController.navigate(Register) {
                                popUpTo(Splash) { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                        onNavigateToHome = {
                            navController.navigate(Home) {
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
                        onBackClicked = {
                            if (!navController.popBackStack()) {
                                // fallback: delegate to system back
                                (navController.context as? ComponentActivity)
                                    ?.onBackPressedDispatcher
                                    ?.onBackPressed()
                            }
                        },
                        onNavigateToHome = {
                            navController.navigate(MainGraph) {
                                popUpTo(AuthGraph) { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                        onNavigateToRegister = { navController.navigate(Register) },
                        onNavigateToForgotPassword = { navController.navigate(ResetPassword) }
                    )
                }
                composable<Register>(
                    enterTransition = {
                        slideIntoContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(500)
                        )
                    },
                    exitTransition = {
                        slideOutOfContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(500)
                        )
                    }
                ) {
                    val viewModel = koinViewModel<RegisterViewModel>()
                    RegisterScreen(
                        modifier = modifier,
                        uiState = viewModel.uiState.collectAsState().value,
                        onEvent = viewModel::onEvent,
                        uiEffect = viewModel.uiEffect,
                        onNavigateToHome = {
                            navController.navigate(MainGraph) {
                                popUpTo(AuthGraph) { inclusive = true }
                                launchSingleTop = true
                            }
                        },
                        onNavigateToLogin = {
                            navController.navigate(Login)
                        },
                        onBackClicked = { navController.popBackStack() },
                    )
                }
                composable<ResetPassword>(
                    enterTransition = {
                        slideIntoContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(500)
                        )
                    },
                    exitTransition = {
                        slideOutOfContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(500)
                        )
                    }
                ) {
                    val resetPasswordViewModel = koinViewModel<ResetPasswordViewModel>()
                    ResetPasswordScreen(
                        uiState = resetPasswordViewModel.uiState.collectAsState().value,
                        onEvent = resetPasswordViewModel::onEvent,
                        uiEffect = resetPasswordViewModel.uiEffect,
                        onNavigateToLoginScreen = {
                            navController.navigate(Login)
                        },
                        onBackClicked = { navController.popBackStack() }
                    )
                }
            }


            // Main app flow

            navigation<MainGraph>(
                startDestination = Home,
            ) {
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
                        navigateToMousepad = { navController.navigate(Mouse) },
                        navigateToPcGuide = { navController.navigate(PCGuide) },
                        navigateToOptions = { navController.navigate(Options) },
                        onNavigateToScannerScreen = { navController.navigate(ConnectionGraph) },
                    )
                }


                // Connection flow nested inside MainGraph, with its own back stack and transitions

                navigation<ConnectionGraph>(
                    startDestination = Scanner
                ) {
                    composable<Scanner>(
                        enterTransition = {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(500)
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                                animationSpec = tween(500)
                            )
                        }
                    ) {
                        val scannerViewModel = koinViewModel<ScannerViewModel>()
                        ScannerScreen(
                            viewModel = scannerViewModel,
                            onBackClicked = { navController.popBackStack() },
                            onNavigateToManualConnection = { navController.navigate(ManualConnection) },
                            onNavigateToHomeScreen = {
                                navController.navigate(MainGraph) {
                                    popUpTo(ConnectionGraph) { inclusive = true }
                                    launchSingleTop = true
                                }
                            }
                        )
                    }

                    composable<ManualConnection>(
                        enterTransition = {
                            slideIntoContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(500)
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                                animationSpec = tween(500)
                            )
                        }
                    ) {
                        ManualConnectionScreen(
                            onBackClicked = { navController.popBackStack() }
                        )
                    }

                }

                composable<Mouse> {
                    MouseScreen(
                        onEvent = mouseViewModel::onEvent,
                    )
                }
                composable<PCGuide>(
                    enterTransition = {
                        slideIntoContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(500)
                        )
                    },
                    exitTransition = {
                        slideOutOfContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(500)
                        )
                    }
                ) {
                    PcGuideScreen(
                        onBackClicked = { navController.popBackStack() }
                    )
                }
                composable<Options>(
                    enterTransition = {
                        slideIntoContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(500)
                        )
                    },
                    exitTransition = {
                        slideOutOfContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(500)
                        )
                    }
                ) {

                    val viewModel = koinViewModel<OptionsViewModel>()
                    OptionsScreen(
                        uiEffect = viewModel.uiEffect,
                        onEvent = viewModel::onEvent,
                        onNavigateToHome = { navController.popBackStack() },
                        onNavigateToLogin = {
                            navController.navigate(AuthGraph) {
                                popUpTo(0) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }


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
data object ResetPassword

@Serializable
data object Home

@Serializable
data object Mouse

@Serializable
data object PCGuide

@Serializable
data object Options


@Serializable
data object AuthGraph

@Serializable
data object MainGraph

@Serializable
data object ConnectionGraph

@Serializable
data object Scanner

@Serializable
data object ManualConnection


