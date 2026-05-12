package com.slemenceu.taptrack.mousepad.ui.home_screen.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slemenceu.taptrack.R
import com.slemenceu.taptrack.core.composables.BackgroundThemeCard
import com.slemenceu.taptrack.ui.theme.darkBlue800
import com.slemenceu.taptrack.ui.theme.lightGrey400
import com.slemenceu.taptrack.ui.theme.lightGrey800

@Composable
fun FirstTimeUserHomeSection(modifier: Modifier = Modifier) {
    BackgroundThemeCard() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(14.dp)
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.tap_track_icon),
                contentDescription = "App Icon",
                tint = Color.Unspecified,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Welcome to TapTrack",
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Turn your phone into a wireless \nmouse for any PC or Mac.",
                fontSize = 11.sp,
                color = lightGrey400,
                textAlign = TextAlign.Center,
                modifier = Modifier
            )
            Spacer(Modifier.height(11.dp))
            HintStepCard(
                "1",
                "Install TapTrack on your PC",
                "Windows & Mac supported"
            )
            Spacer(Modifier.height(11.dp))
            HintStepCard(
                "2",
                "Join same WiFi on both devices",
                "Required for Connection"
            )
            Spacer(Modifier.height(11.dp))
            HintStepCard(
                "3",
                "Scan the QR code on PC screen",
                "Tap the button below to start"
            )
        }
    }
}


@Preview
@Composable
private fun FirstTimeUserHomeSectionPreview() {
    FirstTimeUserHomeSection()
}