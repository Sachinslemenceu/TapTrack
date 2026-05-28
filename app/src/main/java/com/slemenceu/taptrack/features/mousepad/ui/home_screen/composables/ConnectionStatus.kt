package com.slemenceu.taptrack.features.mousepad.ui.home_screen.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slemenceu.taptrack.features.connection.domain.models.ConnectionStatus
import com.slemenceu.taptrack.ui.theme.blue500
import com.slemenceu.taptrack.ui.theme.green500
import com.slemenceu.taptrack.ui.theme.red500

@Composable
fun ConnectionStatus(
    connectionStatus: ConnectionStatus,
    modifier: Modifier = Modifier
) {
    val borderColor = when(connectionStatus){
        ConnectionStatus.Connected -> green500.copy(alpha = 0.22f)
        is ConnectionStatus.Connecting -> blue500.copy(alpha = 0.22f)
        ConnectionStatus.Disconnected -> red500.copy(alpha = 0.22f)
        is ConnectionStatus.Failed -> red500.copy(alpha = 0.22f)
    }

    val fillColor = when(connectionStatus){
        ConnectionStatus.Connected -> green500.copy(alpha = 0.10f)
        is ConnectionStatus.Connecting -> blue500.copy(alpha = 0.10f)
        ConnectionStatus.Disconnected -> red500.copy(alpha = 0.10f)
        is ConnectionStatus.Failed -> red500.copy(alpha = 0.10f)
    }



    val textColor = when(connectionStatus){
        ConnectionStatus.Connected -> green500
        is ConnectionStatus.Connecting -> blue500
        ConnectionStatus.Disconnected -> red500
        is ConnectionStatus.Failed -> red500
    }
    val text = when(connectionStatus){
        ConnectionStatus.Connected -> "Connected"
        is ConnectionStatus.Connecting -> "Connecting"
        ConnectionStatus.Disconnected -> "Not Connected"
        is ConnectionStatus.Failed -> "Failed"
    }

    Surface(
        color = fillColor,
        border = BorderStroke(1.dp, borderColor),
        shape = RoundedCornerShape(15.dp),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.padding(horizontal = 30.dp, vertical = 5.dp)
        ) {

            Box(
                modifier = Modifier
                    .size(7.dp)
                    .background(color = textColor, shape = CircleShape)
            ) { }

            Text(
                text = text,
                color = textColor,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}


@Preview
@Composable
private fun ConnectionStatusPreview1() {
    ConnectionStatus(ConnectionStatus.Connected)
}

@Preview
@Composable
private fun ConnectionStatusPreview2() {
    ConnectionStatus(ConnectionStatus.Connecting())
}
@Preview
@Composable
private fun ConnectionStatusPreview3() {
    ConnectionStatus(ConnectionStatus.Disconnected)
}

