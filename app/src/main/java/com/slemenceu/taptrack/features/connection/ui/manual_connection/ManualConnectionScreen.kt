package com.slemenceu.taptrack.features.connection.ui.manual_connection

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slemenceu.taptrack.R
import com.slemenceu.taptrack.features.authentication.ui.splash_screen.composable.MyTextField
import com.slemenceu.taptrack.core.ui.composables.AppTopBar
import com.slemenceu.taptrack.core.ui.composables.BackgroundThemeCard
import com.slemenceu.taptrack.core.ui.composables.MyPrimaryButton
import com.slemenceu.taptrack.core.ui.composables.MySecondaryButton
import com.slemenceu.taptrack.ui.theme.blue500
import com.slemenceu.taptrack.ui.theme.darkBlue900
import com.slemenceu.taptrack.ui.theme.lightGrey400

@Composable
fun ManualConnectionScreen(
    onBackClicked: () -> Unit,
    onNavigateToHomeScreen: (String) -> Unit,
    modifier: Modifier = Modifier
) {


    var ipAddress by remember { mutableStateOf("") }
    var portNo by remember { mutableStateOf("") }

    AppTopBar(
        onBackClicked = onBackClicked,
        title = buildAnnotatedString { append("Manual Connection") }
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
            Spacer(Modifier.height(16.dp))

            MyTextField(
                value = ipAddress,
                onValueChange = {
                    ipAddress = it
                },
                leadingIcon = ImageVector.vectorResource(R.drawable.ip_address_icon),
                placeholder = "xxx.xxx.xxx.xxx",
                title = "IP Address"
            )
            Spacer(Modifier.height(16.dp))
            MyTextField(
                value = portNo,
                onValueChange = {
                    portNo = it
                },
                leadingIcon = ImageVector.vectorResource(R.drawable.port_no_icon),
                placeholder = "xxxxx",
                title = "Port"
            )
            Spacer(Modifier.height(16.dp))
            BackgroundThemeCard(
                backgroundColor = blue500.copy(0.05f),
                borderColor = blue500.copy(0.15f),
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "How to find the IP address",
                        fontSize = 12.sp,
                        color = blue500,
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Open TapTrack on your PC. The IP \naddress appears below the QR code.",
                        fontSize = 12.sp,
                        color = lightGrey400,
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
            MyPrimaryButton(
                text = "Connect",
                onClick = {
                    val connectionInfo = "$ipAddress:$portNo:1234:MyDevice:Wifi-Network"
                    onNavigateToHomeScreen(connectionInfo)
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            MySecondaryButton(
                text = "Back to QR Scan",
                textColor = lightGrey400,
                onClick = onBackClicked,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}


@Preview
@Composable
private fun ManualConnectionScreenPreview() {
    Scaffold(
        containerColor = darkBlue900
    ) {
        ManualConnectionScreen(
            modifier = Modifier.padding(it),
            onBackClicked = {},
            onNavigateToHomeScreen = {}
        )
    }
}
