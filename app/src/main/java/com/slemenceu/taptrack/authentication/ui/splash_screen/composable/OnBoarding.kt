package com.slemenceu.taptrack.authentication.ui.splash_screen.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.slemenceu.taptrack.R
import com.slemenceu.taptrack.ui.theme.pink10
import com.slemenceu.taptrack.ui.theme.violet10


@Composable
fun OnBoarding(
    modifier: Modifier = Modifier,
    onGetStartedClicked: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(violet10)
    ){
        Canvas(modifier = Modifier.fillMaxSize().zIndex(0f)) {
            drawCircle(
                color = pink10,
                center = Offset(x = size.center.x/4, y = size.center.y),
                radius = size.minDimension/2
            )
        }
        Image(
            painter = painterResource(R.drawable.mouse_img),
            contentDescription = "mouse",
            modifier = Modifier
                .defaultMinSize(200.dp)
                .padding(60.dp)
                .align(Alignment.Center)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
                .zIndex(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,

            ) {

            Text(
                text = "You don’t need\n" +
                        "mouse anymore!",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            MyButton(
                modifier = Modifier.padding(bottom = 30.dp),
                text = "Get Started"
            ) {
                onGetStartedClicked()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnBoardingPreview() {
    OnBoarding(
        onGetStartedClicked = {}
    )
}