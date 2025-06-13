package com.slemenceu.taptrack.mousepad.ui.mousepad_screen

import android.app.Activity
import android.content.pm.ActivityInfo
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.slemenceu.taptrack.mousepad.ui.home_screen.HomeUiState
import com.slemenceu.taptrack.mousepad.ui.mousepad_screen.composables.*

@Composable
fun MouseScreen(
    onEvent: (MouseUiEvent) -> Unit,
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        if (context is Activity) {
            context.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        }

    }

    DisposableEffect(Unit) {
        onDispose{
            val activity = context as? Activity
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }

    TouchPad(
        onEvent = onEvent
    )

}
