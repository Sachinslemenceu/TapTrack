package com.slemenceu.taptrack.features.mousepad.ui.mousepad_screen

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.slemenceu.taptrack.features.mousepad.ui.mousepad_screen.composables.TouchPad

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
