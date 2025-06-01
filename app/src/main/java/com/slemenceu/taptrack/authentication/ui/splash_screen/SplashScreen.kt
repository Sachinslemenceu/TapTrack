package com.slemenceu.taptrack.authentication.ui.splash_screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.slemenceu.taptrack.R
import com.slemenceu.taptrack.authentication.ui.splash_screen.composable.OnBoarding
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = koinViewModel(),
    onGetStartedClicked: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    Scaffold {

        val TAG = "SplashScreen"
        val uiState = viewModel.uiState.collectAsState().value
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash_ani))
        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = 1,
            speed = 1f,
        )
        LaunchedEffect(Unit) {
            viewModel.uiEffect.collect {
                when (it) {
                    is SplashUiEffect.NavigateToLogin -> {
                        Log.d(TAG, "SplashScreen: NavigateToLogin")
                        onGetStartedClicked()
                    }
                    SplashUiEffect.NavigateToHome -> {
                        Log.d(TAG, "SplashScreen: NavigateToHome")
                        onNavigateToHome()
                    }
                }
            }
        }
        LaunchedEffect(progress) {
            if (progress == 1f) {
                delay(2000)
                viewModel.onEvent(SplashUiEvent.OnAnimationDone)
            }
        }
        if (!uiState.isAnimationFinished) {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(it),
                contentAlignment = Alignment.Center
            ) {
                LottieAnimation(
                    composition = composition,
                    progress = { progress }
                )

            }
        }
        else {

                OnBoarding(
                    modifier = modifier.padding(it)
                ){
                    viewModel.onEvent(SplashUiEvent.OnGetStartedClicked)
                    Log.d(TAG, "SplashScreen: OnGetStartedClicked")
                }

        }
    }

}