package com.slemenceu.taptrack.features.mousepad.ui.trackpad_screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slemenceu.taptrack.R
import com.slemenceu.taptrack.core.ui.composables.MyDialog
import com.slemenceu.taptrack.core.ui.composables.MyIconButton
import com.slemenceu.taptrack.core.ui.composables.MySecondaryButton
import com.slemenceu.taptrack.features.mousepad.ui.trackpad_screen.composables.TouchPad
import com.slemenceu.taptrack.ui.theme.darkBlue900
import com.slemenceu.taptrack.ui.theme.green500
import com.slemenceu.taptrack.ui.theme.lightGrey300
import com.slemenceu.taptrack.ui.theme.lightGrey400

@Composable
fun TrackpadScreen(
    onEvent: (TrackpadUiEvent) -> Unit,
    onNavigateToHome:() -> Unit,
    modifier: Modifier = Modifier
) {

    var isDisconnectDialogOpen by remember { mutableStateOf(false) }

    BackHandler() {
        isDisconnectDialogOpen = true
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(color = green500, shape = CircleShape)
                    .size(5.dp)
            )
            Spacer(Modifier.width(4.dp))
            Text(
                "Macbook pro",
                fontSize = 11.sp,
                color = green500
            )
            Spacer(Modifier.width(20.dp))
            Text(
                "12 ms",
                fontSize = 11.sp,
                color = lightGrey300
            )
            Spacer(Modifier.weight(1f))
            MyIconButton(
                icon = ImageVector.vectorResource(R.drawable.scope_icon),
                iconColor = lightGrey400,
                onClick = onNavigateToHome
            )
            Spacer(Modifier.width(10.dp))
            MyIconButton(
                icon = Icons.Default.Clear,
                iconColor = lightGrey400,
                onClick = {
                    isDisconnectDialogOpen = true
                }
            )

        }
        TouchPad(
            onEvent = onEvent,
            modifier = Modifier
                .weight(1f)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp)
        ) {
           MySecondaryButton(
               text = "Left Click",
               textColor = lightGrey400,
               modifier = Modifier
                   .weight(1f)
           ) { }
            Spacer(Modifier.width(20.dp))
           MySecondaryButton(
               text = "Right Click",
               textColor = lightGrey400,
               modifier = Modifier
                   .weight(1f)
           ) { }
        }
        Spacer(Modifier.weight(0.1f))
    }

    if (isDisconnectDialogOpen){
        MyDialog(
            header = "Do you want to dsconnect?",
            description = "This will disconnect the app from the PC.",
            icon = ImageVector.vectorResource(R.drawable.cancel_phn_icon),
            onConfirm = {},
            onDismiss = {
                isDisconnectDialogOpen = false
            },
            primaryButtonText = "Yes",
            secondaryButtonText = "No, Cancel"
        )
    }
}

@Preview
@Composable
private fun TrackpadScreenPreview() {
    Scaffold(
        containerColor = darkBlue900
    ) {
        TrackpadScreen(
            modifier = Modifier.padding(it),
            onEvent = {},
            onNavigateToHome = {}
        )
    }
}
