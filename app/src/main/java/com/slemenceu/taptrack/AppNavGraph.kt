package com.slemenceu.taptrack

import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.slemenceu.taptrack.features.authentication.domain.AuthRepository
import com.slemenceu.taptrack.features.authentication.domain.usecase.LogoutUseCase
import com.slemenceu.taptrack.features.authentication.ui.login_screen.LoginScreen
import com.slemenceu.taptrack.features.authentication.ui.login_screen.LoginViewModel
import com.slemenceu.taptrack.features.authentication.ui.register_screen.RegisterScreen
import com.slemenceu.taptrack.features.authentication.ui.register_screen.RegisterViewModel
import com.slemenceu.taptrack.features.authentication.ui.reset_password.ResetPasswordScreen
import com.slemenceu.taptrack.features.authentication.ui.reset_password.ResetPasswordViewModel
import com.slemenceu.taptrack.features.authentication.ui.splash_screen.SplashScreen
import com.slemenceu.taptrack.features.authentication.ui.splash_screen.SplashViewModel
import com.slemenceu.taptrack.features.connection.ui.manual_connection.ManualConnectionScreen
import com.slemenceu.taptrack.features.connection.ui.scanner.ScannerScreen
import com.slemenceu.taptrack.features.connection.ui.scanner.ScannerViewModel
import com.slemenceu.taptrack.features.mousepad.ui.home_screen.HomeScreen
import com.slemenceu.taptrack.features.mousepad.ui.home_screen.HomeUiEvent
import com.slemenceu.taptrack.features.mousepad.ui.home_screen.HomeViewModel
import com.slemenceu.taptrack.features.mousepad.ui.settings_screen.SettingsScreen
import com.slemenceu.taptrack.features.mousepad.ui.settings_screen.about.AboutScreen
import com.slemenceu.taptrack.features.mousepad.ui.settings_screen.profile.ProfileScreen
import com.slemenceu.taptrack.features.mousepad.ui.settings_screen.profile.ProfileViewModel
import com.slemenceu.taptrack.features.mousepad.ui.settings_screen.security.SecurityScreen
import com.slemenceu.taptrack.features.mousepad.ui.settings_screen.security.SecurityViewModel
import com.slemenceu.taptrack.features.mousepad.ui.settings_screen.support.SupportScreen
import com.slemenceu.taptrack.features.mousepad.ui.trackpad_screen.TrackpadScreen
import com.slemenceu.taptrack.features.mousepad.ui.trackpad_screen.TrackpadViewModel
import com.slemenceu.taptrack.ui.theme.darkBlue900
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun AppNavGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val trackpadViewModel = koinViewModel<TrackpadViewModel>()
    val logoutUseCase = koinInject<LogoutUseCase>()
    val scope = rememberCoroutineScope()

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
                            navController.navigate(MainGraph) {
                                popUpTo(AuthGraph) { inclusive = true }
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
                startDestination = Home(),
            ) {
                composable<Home>(
                    enterTransition = {
                        slideIntoContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Up,
                            animationSpec = tween(500)
                        ) + fadeIn(animationSpec = tween(500))
                    }
                ) { backStackEntry ->
                    val args = backStackEntry.toRoute<Home>()
                    val connectionInfo = args.connectionInfo
                    val viewModel = koinViewModel<HomeViewModel>()

                    LaunchedEffect(connectionInfo) {
                        connectionInfo?.let {
                            viewModel.onEvent(HomeUiEvent.Connect(it))
                        }
                    }

                    HomeScreen(
                        uiState = viewModel.uiState.collectAsState().value,
                        onEvent = viewModel::onEvent,
                        onNavigateToMousepad = { navController.navigate(Mouse) },
                        onNavigateToScannerScreen = { navController.navigate(ConnectionGraph) },
                        onNavigateToSettings = { navController.navigate(SettingsGraph) }
                    )
                }

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
                        val authRepository = koinInject<AuthRepository>()
                        ScannerScreen(
                            viewModel = scannerViewModel,
                            onBackClicked = { navController.popBackStack() },
                            onNavigateToManualConnection = {
                                navController.navigate(ManualConnection)
                            },
                            onNavigateToHomeScreen = { connectionInfo ->
                                scope.launch {
                                    authRepository.saveFirstLoginStatus(true)
                                }
                                navController.navigate(Home(connectionInfo)) {
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
                            onBackClicked = { navController.popBackStack() },
                            onNavigateToHomeScreen = { connectionInfo ->
                                navController.navigate(Home(connectionInfo)) {
                                    popUpTo(ConnectionGraph) { inclusive = true }
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                }

                composable<Mouse> {
                    TrackpadScreen(
                        onEvent = trackpadViewModel::onEvent,
                        uiState = trackpadViewModel.uiState.collectAsState().value,
                        uiEffect = trackpadViewModel.uiEffect,
                        onNavigateToHome = {isDisconnected->
                            if (isDisconnected){
                                navController.navigate(Home()){
                                    popUpTo(0) { inclusive = true }
                                    launchSingleTop = true
                                }
                            } else{
                                navController.navigate(Home())
                            }
                        }
                    )
                }
                navigation<SettingsGraph>(
                    startDestination = Settings
                ) {
                    composable<Settings>(
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
                        SettingsScreen(
                            onBackClicked = { navController.popBackStack() },
                            onNavigateToProfile = { navController.navigate(Profile) },
                            onNavigateToSecurity = { navController.navigate(Security) },
                            onNavigateToSupport = { navController.navigate(Support) },
                            onNavigateToAbout = { navController.navigate(About) },
                            onLogout = {
                                scope.launch {
                                    val result = logoutUseCase()
                                    if (result) {
                                        navController.navigate(Login) {
                                            popUpTo(0) { inclusive = true }
                                            launchSingleTop = true
                                        }
                                    }
                                }
                            }
                        )
                    }
                    composable<Profile>(
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
                        val profileViewmodel = koinViewModel<ProfileViewModel>()
                        ProfileScreen(
                            uiState = profileViewmodel.uiState.collectAsState().value,
                            onEvent = profileViewmodel::onEvent,
                            uiEffect = profileViewmodel.uiEffect,
                            onBackClicked = { navController.popBackStack() }
                        )
                    }
                    composable<Security>(
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
                        val securityViewModel = koinViewModel<SecurityViewModel>()
                        SecurityScreen(
                            uiState = securityViewModel.uiState.collectAsState().value,
                            onEvent = securityViewModel::onEvent,
                            uiEffect = securityViewModel.uiEffect,
                            onBackClicked = { navController.popBackStack() }
                        )
                    }
                    composable<Support>(
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
                        SupportScreen(
                            onBackClicked = { navController.popBackStack() }
                        )
                    }
                    composable<About>(
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
                        AboutScreen(
                            onBackClicked = { navController.popBackStack() }
                        )
                    }
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
data class Home(val connectionInfo: String? = null)

@Serializable
data object Mouse

@Serializable
data object PCGuide

@Serializable
data object Options

@Serializable
data object Settings

@Serializable
data object Profile

@Serializable
data object Security

@Serializable
data object Support

@Serializable
data object About

@Serializable
data object AuthGraph

@Serializable
data object MainGraph

@Serializable
data object ConnectionGraph

@Serializable
data object SettingsGraph

@Serializable
data object Scanner

@Serializable
data object ManualConnection
