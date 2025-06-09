package com.slemenceu.taptrack.mousepad.ui.home_screen.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slemenceu.taptrack.R
import com.slemenceu.taptrack.ui.theme.darkGrey

@Composable
fun WifiStatusCard(
    ssid: String,
    isConnected: Boolean,
    openWifiSettings: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Current Network :",
                    color = darkGrey,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraLight
                )
                Text(
                    text = ssid,
                    color = darkGrey,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Switch(
                    checked = isConnected,
                    onCheckedChange = {it ->
                        if (it){
                            openWifiSettings()
                        } else {
                            openWifiSettings()
                        }
                    },
                    colors = androidx.compose.material3.SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = darkGrey
                    )
                )
            }
            Text(
                "Connect the phone and pc to same network to use TapTrack." +
                        " You need a TapTrack pc app to use the functionality," +
                        " if you haven’t install the app already ," +
                        " click the button below.",
                fontSize = 12.sp,
                fontWeight = FontWeight.Light,
                color = darkGrey,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            IconButton(
                onClick = {},
                modifier = Modifier.padding(16.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.link_img),
                    contentDescription = null,
                    Modifier.size(40.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun WifiStatusCardPreview() {
    WifiStatusCard(ssid = "not conneced", isConnected = false, openWifiSettings = {})
}