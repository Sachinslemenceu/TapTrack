package com.slemenceu.taptrack.authentication.ui.splash_screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.slemenceu.taptrack.R
import com.slemenceu.taptrack.authentication.ui.splash_screen.composable.OnBoarding
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    uiState: SplashUiState,
    onEvent: (SplashUiEvent) -> Unit,
    uiEffect: SharedFlow<SplashUiEffect>,
    onGetStartedClicked: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    Scaffold(
        containerColor = Color.White
    ) {

        val TAG = "SplashScreen"
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash_ani))
        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = 1,
            speed = 1f,
        )
        LaunchedEffect(Unit) {
            uiEffect.collect {
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
                onEvent(SplashUiEvent.OnAnimationDone)
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
                    onEvent(SplashUiEvent.OnGetStartedClicked)
                    Log.d(TAG, "SplashScreen: OnGetStartedClicked")
                }

        }
    }

}