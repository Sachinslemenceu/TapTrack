package com.slemenceu.taptrack.features.mousepad.ui.settings_screen.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slemenceu.taptrack.core.ui.composables.AppTopBar
import com.slemenceu.taptrack.ui.theme.lightGrey300
import com.slemenceu.taptrack.ui.theme.lightGrey400

@Composable
fun AboutScreen(
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppTopBar(
        onBackClicked = onBackClicked,
        title = buildAnnotatedString { append("About") }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(100.dp))

            Text(
                text = "TapTrack",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = "v1.0.0 (Stable)",
                color = lightGrey400,
                fontSize = 14.sp
            )

            Spacer(Modifier.height(32.dp))

            Text(
                text = "TapTrack is a modern remote trackpad application designed for speed and simplicity. It allows you to control your PC mouse and keyboard over WiFi with ultra-low latency.",
                color = lightGrey300,
                fontSize = 14.sp,
                lineHeight = 22.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Spacer(Modifier.weight(1f))
            
            Text(
                text = "© 2024 Slemenceu TapTrack. \nAll rights reserved.",
                color = lightGrey400,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
            
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Preview
@Composable
private fun AboutScreenPreview() {
    AboutScreen(onBackClicked = {})
}
