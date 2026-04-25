package com.slemenceu.taptrack.authentication.ui.splash_screen

import android.util.Log
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.slemenceu.taptrack.R
import com.slemenceu.taptrack.authentication.ui.splash_screen.onboarding.OnBoardingScreen
import com.slemenceu.taptrack.ui.theme.darkBlue900
import com.slemenceu.taptrack.ui.theme.green500
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    uiState: SplashUiState,
    onEvent: (SplashUiEvent) -> Unit,
    uiEffect: SharedFlow<SplashUiEffect>,
    onNavigateToLogin: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    Scaffold(
        containerColor = darkBlue900
    ) {

        val TAG = "SplashScreen"
        val iconScale by rememberInfiniteTransition(label = "iconScaleTransition").animateFloat(
            initialValue = 0.92f,
            targetValue = 1.08f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 800),
                repeatMode = RepeatMode.Reverse
            ),
            label = "iconScale"
        )
        LaunchedEffect(Unit) {
            uiEffect.collect {
                when (it) {
                    is SplashUiEffect.NavigateToLogin -> {
                        Log.d(TAG, "SplashScreen: NavigateToLogin")
                        onNavigateToLogin()
                    }

                    SplashUiEffect.NavigateToHome -> {
                        Log.d(TAG, "SplashScreen: NavigateToHome")
                        onNavigateToHome()
                    }
                }
            }
        }
        LaunchedEffect(Unit) {
            delay(2000)
            onEvent(SplashUiEvent.OnAnimationDone)
        }
        if (!uiState.isAnimationFinished) {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(it),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        modifier = Modifier.graphicsLayer(
                            scaleX = iconScale,
                            scaleY = iconScale,
                        ),
                        imageVector = ImageVector.vectorResource(R.drawable.tap_track_icon),
                        contentDescription = "App Icon",
                        tint = Color.Unspecified,
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = Color.White)) {
                                append("Tap")
                            }
                            withStyle(style = SpanStyle(color = green500)) {
                                append("Track")
                            }
                        },
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )

                }


            }
        } else {
            OnBoardingScreen(
                onGetStartedClicked = {
                    onEvent(SplashUiEvent.OnGetStartedClicked)
                },
                onSignInClicked = {},
                modifier = modifier.padding(it)
            )
        }
    }

}


@Preview
@Composable
private fun SplashScreenPreview() {
    SplashScreen(
        uiState = SplashUiState(),
        onEvent = {},
        uiEffect = MutableSharedFlow(),
        onNavigateToLogin = {},
        onNavigateToHome = {}
    )
}