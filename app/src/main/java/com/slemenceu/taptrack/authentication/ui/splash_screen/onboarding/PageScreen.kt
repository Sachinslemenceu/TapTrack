package com.slemenceu.taptrack.authentication.ui.splash_screen.onboarding

import android.graphics.drawable.Icon
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.slemenceu.taptrack.R
import com.slemenceu.taptrack.ui.theme.darkBlue800
import com.slemenceu.taptrack.ui.theme.lightGrey800

@Composable
fun PageScreen(
    animationIcon: Int,
    descriptionIcon: Int,
    title: String,
    description: String
) {

    val iconScale by rememberInfiniteTransition(label = "iconScaleTransition").animateFloat(
        initialValue = 0.92f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800),
            repeatMode = RepeatMode.Reverse
        ),
        label = "iconScale"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Icon(
            imageVector = ImageVector.vectorResource(animationIcon),
            contentDescription = "App Icon",
            tint = Color.Unspecified,
            modifier = Modifier.graphicsLayer(
                scaleX = iconScale,
                scaleY = iconScale,
            ),
        )
        Spacer(modifier = Modifier.height(60.dp))
        Card(
            border = BorderStroke(
                width = 1.dp,
                color = darkBlue800
            ),
            colors = CardDefaults.cardColors(
                containerColor = lightGrey800
            ),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(14.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(descriptionIcon),
                    contentDescription = "OnBoarding Icon",
                    tint = Color.Unspecified,
                )
                Spacer(modifier = Modifier.width(14.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = title,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFEFF1F5),
                    )
                    Text(
                        text = description,
                        fontSize = 11.sp,
                        color = Color(0xFF6B7280),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun OnBoardingScreenPreview() {

    PageScreen(
        animationIcon = R.drawable.onboarding_icon_1,
        descriptionIcon = R.drawable.onboarding_icon_1,
        title = stringResource(id = R.string.annoy_title),
        description = stringResource(R.string.annoy_desc)
    )
}