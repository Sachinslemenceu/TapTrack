package com.slemenceu.taptrack.features.mousepad.ui.home_screen.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
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
import com.slemenceu.taptrack.core.ui.composables.BackgroundThemeCard
import com.slemenceu.taptrack.features.connection.domain.models.ConnectionStatus
import com.slemenceu.taptrack.features.connection.domain.models.ConnectionStep
import com.slemenceu.taptrack.ui.theme.blue500
import com.slemenceu.taptrack.ui.theme.darkBlue800
import com.slemenceu.taptrack.ui.theme.green500
import com.slemenceu.taptrack.ui.theme.lightGrey400
import com.slemenceu.taptrack.ui.theme.red500

@Composable
fun ConnectionCard(
    connectionStatus: ConnectionStatus,
    latency: Int = 0,
    onScanQrClicked: () -> Unit,
    onCancelClicked: () -> Unit,
    onOpenTrackpadClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor = when (connectionStatus) {
        ConnectionStatus.Connected -> green500.copy(alpha = 0.22f)
        is ConnectionStatus.Connecting -> blue500.copy(alpha = 0.22f)
        ConnectionStatus.Disconnected -> darkBlue800
        is ConnectionStatus.Failed -> darkBlue800
    }
    BackgroundThemeCard(
        borderColor = borderColor,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            ConnectionStatus(connectionStatus = connectionStatus)
            if (connectionStatus == ConnectionStatus.Disconnected || connectionStatus is ConnectionStatus.Failed) {
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
            } else if (connectionStatus is ConnectionStatus.Connecting) {
                Spacer(Modifier.height(22.dp))

                ConnectionProgressBar(
//                    progress = progress,
                )
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
                    onClick = onCancelClicked
                ) {
                    Text(
                        text = "Cancel",
                        fontSize = 13.sp,
                        color = red500,
                        fontWeight = FontWeight.Bold
                    )
                }

            } else {
                Spacer(Modifier.height(7.dp))
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.signal_icon),
                    contentDescription = "Signal Icon",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(40.dp)
                )
                Text(
                    text = "${latency}ms",
                    fontSize = 54.sp,
                    color = green500,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(Modifier.height(24.dp))
                Button(
                    onClick = onOpenTrackpadClicked,
                    modifier = modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = green500
                    ),
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Open Trackpad",
                            fontSize = 15.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "open Trackpad",
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

        }
    }
}


@Preview
@Composable
private fun ConnectionCardPreview1() {
    ConnectionCard(
        connectionStatus = ConnectionStatus.Connected,
        onScanQrClicked = {},
        onCancelClicked = {},
        onOpenTrackpadClicked = {}
    )

}

@Preview
@Composable
private fun ConnectionCardPreview2() {
    ConnectionCard(
        connectionStatus = ConnectionStatus.Connecting(),
        onScanQrClicked = {},
        onCancelClicked = {},
        onOpenTrackpadClicked = {}
    )

}

@Preview
@Composable
private fun ConnectionCardPreview3() {
    ConnectionCard(
        connectionStatus = ConnectionStatus.Connecting(step = ConnectionStep.ESTABLISHING_UDP_CONNECTION),
        onScanQrClicked = {},
        onCancelClicked = {},
        onOpenTrackpadClicked = {}
    )

}

@Preview
@Composable
private fun ConnectionCardPreview4() {
    ConnectionCard(
        connectionStatus = ConnectionStatus.Connecting(step = ConnectionStep.VERIFYING_LATENCY),
        onScanQrClicked = {},
        onCancelClicked = {},
        onOpenTrackpadClicked = {}
    )

}

@Preview
@Composable
private fun ConnectionCardPreview5() {
    ConnectionCard(
        connectionStatus = ConnectionStatus.Disconnected,
        onScanQrClicked = {},
        onCancelClicked = {},
        onOpenTrackpadClicked = {}
    )

}
