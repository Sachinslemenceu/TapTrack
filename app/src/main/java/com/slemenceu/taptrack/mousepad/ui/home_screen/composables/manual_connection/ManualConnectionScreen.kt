package com.slemenceu.taptrack.mousepad.ui.home_screen.composables.manual_connection

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slemenceu.taptrack.core.composables.AppTopBar
import com.slemenceu.taptrack.ui.theme.lightGrey400

@Composable
fun ManualConnectionScreen(
    onBackClicked: () -> Unit = {},
    modifier: Modifier = Modifier) {
    AppTopBar(
        onBackClicked = onBackClicked,
        title = buildAnnotatedString { append("Scan to Connect") }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxWidth()
        ) {
            Spacer(Modifier.height(75.dp))

            Text(
                text = "Enter the IP shown in TapTrack PC app. \nBoth devices must be on the same WiFi.",
                fontSize = 12.sp,
                color = lightGrey400,
                modifier = Modifier
                    .align(Alignment.Start)
            )
        }
    }
}


@Preview
@Composable
private fun ManualConnectionScreenPreview() {
    ManualConnectionScreen()
}