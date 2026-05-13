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
import com.slemenceu.taptrack.ui.theme.blue500
import com.slemenceu.taptrack.ui.theme.red500

@Composable
fun ConnectionStatus(
    isConnecting: Boolean,
    modifier: Modifier = Modifier
) {
    val borderColor =
        if (isConnecting) blue500.copy(alpha = 0.22f) else red500.copy(alpha = 0.22f)
    val fillColor =
        if (isConnecting) blue500.copy(alpha = 0.10f) else red500.copy(alpha = 0.10f)
    val textColor = if (isConnecting) blue500 else red500
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
                text = if (isConnecting) "Connecting..." else "Not Connected",
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
    ConnectionStatus(isConnecting = true)
}

@Preview
@Composable
private fun ConnectionStatusPreview2() {
    ConnectionStatus(isConnecting = false)
}

