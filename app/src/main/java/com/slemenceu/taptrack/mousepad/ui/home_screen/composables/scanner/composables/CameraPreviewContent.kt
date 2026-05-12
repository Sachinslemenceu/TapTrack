package com.slemenceu.taptrack.mousepad.ui.home_screen.composables.scanner.composables

import android.util.Log
import androidx.camera.compose.CameraXViewfinder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.slemenceu.taptrack.mousepad.ui.home_screen.HomeViewModel

@Composable
fun CameraPreviewContent(
    viewModel: HomeViewModel,
    onQrCodeScanned: (String) -> Unit,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    modifier: Modifier = Modifier
) {

    val surfaceRequest by viewModel.surfaceRequest.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(lifecycleOwner) {
        viewModel.bindToCamera(
            context.applicationContext,
            lifecycleOwner,
            onQrCodeScanned = { scannedText ->
                Log.d("CameraPreciewLogs", "Scanned Text: $scannedText")
                onQrCodeScanned(scannedText)
            })
    }
    surfaceRequest?.let { request ->
        CameraXViewfinder(
            surfaceRequest = request,
            modifier = modifier
        )
    }
}