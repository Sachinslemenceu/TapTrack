package com.slemenceu.taptrack.mousepad.ui.home_screen.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slemenceu.taptrack.R
import com.slemenceu.taptrack.ui.theme.darkViolet
import com.slemenceu.taptrack.ui.theme.mediumViolet

@Composable
fun MousepadSection(
    modifier: Modifier = Modifier,
    isConnected: Boolean,
    onNavigateToMousepad: () -> Unit,
    onConnectToMousepad: (Int) -> Unit
) {
        val image = if (isConnected) R.drawable.connected_mouse_img else R.drawable.disconnected_mouse_img
        val text = if (isConnected) "Click the mouse to open the mousepad" else "Connect the TapTrack app to Pc"
        var showDialog = remember { mutableStateOf(false) }
        Text(
            text = if (isConnected) "Connected" else "Disconnected",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = if (isConnected) mediumViolet else Color.Red,
            modifier = Modifier.padding(20.dp)
        )

            Image(
                painter = painterResource(
                    id = image
                ),
                contentDescription = "Mousepad",
                modifier = Modifier
                    .clickable(
                        onClick = {
                            if(!isConnected){
                                showDialog.value = true
                            } else{
                            onNavigateToMousepad()
                            }
                        }
                    )
                    .size(100.dp)
            )
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(20.dp)
        )
    OtpDialog(
        showDialog = showDialog.value,
        onDismiss = {
            showDialog.value = false
        },
        onConfirm = {passcode ->
            onConnectToMousepad(
                passcode.toInt()
            )
        }
    )
}

@Preview
@Composable
fun MousepadSectionPreview() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        MousepadSection(
            isConnected = true,
            onNavigateToMousepad = {},
            onConnectToMousepad = {}
        )

    }
}