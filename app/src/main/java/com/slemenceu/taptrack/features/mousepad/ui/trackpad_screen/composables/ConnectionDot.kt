package com.slemenceu.taptrack.features.mousepad.ui.trackpad_screen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.slemenceu.taptrack.features.connection.domain.models.ConnectionStatus
import com.slemenceu.taptrack.ui.theme.blue500
import com.slemenceu.taptrack.ui.theme.green500
import com.slemenceu.taptrack.ui.theme.red500

@Composable
fun ConnectionDot(
    connectionStatus: ConnectionStatus,
    modifier: Modifier = Modifier
) {
    val dotColor = if (connectionStatus == ConnectionStatus.Connected) green500 else red500
    Box(
        modifier = Modifier
            .background(color = dotColor, shape = CircleShape)
            .size(5.dp)
    )
}