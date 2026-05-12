package com.slemenceu.taptrack.mousepad.ui.home_screen.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slemenceu.taptrack.R
import com.slemenceu.taptrack.core.composables.BackgroundThemeCard
import com.slemenceu.taptrack.mousepad.ui.home_screen.models.ConnectionStatus
import com.slemenceu.taptrack.ui.theme.green500
import com.slemenceu.taptrack.ui.theme.lightGrey400
import com.slemenceu.taptrack.ui.theme.red500

@Composable
fun ConnectionCard(
    connectionStatus: ConnectionStatus,
    onScanQrClicked: () -> Unit = {},
    onCancel:() -> Unit = {},
    modifier: Modifier = Modifier
) {
    BackgroundThemeCard() {
        val isConnecting = connectionStatus == ConnectionStatus.Connecting

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            if (connectionStatus != ConnectionStatus.Connected) {
                ConnectionStatus(isConnecting = isConnecting)
            }
            if(connectionStatus == ConnectionStatus.NotConnected){
                Spacer(Modifier.height(8.dp))

                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.tap_track_icon),
                    contentDescription = "App Icon",
                    tint = Color.Unspecified,
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Connect to PC",
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = Modifier
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Open TapTrack PC and scan QR",
                    fontSize = 11.sp,
                    color = lightGrey400,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                )
                Spacer(Modifier.height(8.dp))
                Button(
                    onClick = onScanQrClicked,
                    modifier = modifier
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = green500
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.qr_scan_icon),
                            contentDescription = "QR Code Icon",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Scan QR Code",
                            fontSize = 15.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            } else if (connectionStatus == ConnectionStatus.Connecting){
                Spacer(Modifier.height(22.dp))

                ConnectionProgressBar()
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Connecting to MacBook Pro",
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = Modifier
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "192.168.1.42 · Home-WiFi-5G",
                    fontSize = 11.sp,
                    color = lightGrey400,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                )
                Spacer(Modifier.height(8.dp))
                TextButton(
                    onClick = onCancel
                ) {
                    Text(
                        text = "Cancel",
                        fontSize = 13.sp,
                        color = red500 ,
                        fontWeight = FontWeight.Bold
                    )
                }

            }

        }
    }
}


@Preview
@Composable
private fun ConnectionCardPreview1() {
    ConnectionCard(
        connectionStatus = ConnectionStatus.Connecting
    )

}
@Preview
@Composable
private fun ConnectionCardPreview2() {
    ConnectionCard(
        connectionStatus = ConnectionStatus.NotConnected
    )

}
