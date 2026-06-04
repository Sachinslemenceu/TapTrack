package com.slemenceu.taptrack.features.mousepad.ui.home_screen.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slemenceu.taptrack.core.ui.composables.BackgroundThemeCard
import com.slemenceu.taptrack.ui.theme.darkBlue800
import com.slemenceu.taptrack.ui.theme.darkGrey
import com.slemenceu.taptrack.ui.theme.lightGrey300

@Composable
fun ConnectionDetailCard(
    deviceName: String,
    networkName: String,
    modifier: Modifier = Modifier
) {
    BackgroundThemeCard() {
        Column(
            modifier = modifier
                .padding(14.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Connection Details",
                fontSize = 10.sp,
                color = lightGrey300,
            )
            HorizontalDivider(
                thickness = 1.dp,
                color = darkBlue800,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Device",
                    fontSize = 10.sp,
                    color = lightGrey300,
                )
                Text(
                    text = deviceName,
                    fontSize = 10.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(Modifier.height(7.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Network",
                    fontSize = 10.sp,
                    color = lightGrey300,
                )
                Text(
                    text = networkName,
                    fontSize = 10.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConnectionDetailCardPreview() {
    ConnectionDetailCard(
        deviceName = "Macbook",
        networkName = "Home-WiFi-5G"

    )
}