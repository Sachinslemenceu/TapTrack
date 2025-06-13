package com.slemenceu.taptrack.mousepad.ui.mousepad_screen

import android.app.Activity
import android.content.pm.ActivityInfo
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.slemenceu.taptrack.mousepad.ui.mousepad_screen.composables.*

@Composable
fun MouseScreen() {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        if (context is Activity) {
            context.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
    }
    TouchPad(ip = "192.168.1.5", port = 9999)

}
