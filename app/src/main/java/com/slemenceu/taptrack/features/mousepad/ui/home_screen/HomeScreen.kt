package com.slemenceu.taptrack.features.mousepad.ui.home_screen

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.zxing.integration.android.IntentIntegrator
import com.slemenceu.taptrack.core.utils.PermissionManager
import com.slemenceu.taptrack.core.utils.findActivity
import com.slemenceu.taptrack.features.connection.domain.models.ConnectionStatus
import com.slemenceu.taptrack.features.mousepad.ui.home_screen.composables.ConnectionCard
import com.slemenceu.taptrack.features.mousepad.ui.home_screen.composables.ConnectionDetailCard
import com.slemenceu.taptrack.features.mousepad.ui.home_screen.composables.ConnectionStepProgressCard
import com.slemenceu.taptrack.ui.theme.darkBlue800
import com.slemenceu.taptrack.ui.theme.darkBlue900
import com.slemenceu.taptrack.ui.theme.green500
import com.slemenceu.taptrack.ui.theme.lightGrey300
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

//@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    onEvent: (HomeUiEvent) -> Unit,
    uiEffect: SharedFlow<HomeUiEffect>,
    onNavigateToScannerScreen: () -> Unit,
    onNavigateToMousepad: () -> Unit,
) {
    val context = LocalContext.current
    val activity = remember(context) { context.findActivity() }
    val permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_WIFI_STATE,
        Manifest.permission.CAMERA,
    )
    val log = "HomeScreen"
    val permissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { result ->
            Log.d(log, result.toString())
            onEvent(HomeUiEvent.onPermissionResult(result))
            if (result.all { it.value }) {
            }
        }
    )

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val intentResult = IntentIntegrator.parseActivityResult(result.resultCode, result.data)
        val scannedText = intentResult?.contents
        if (scannedText != null) {
            onEvent(HomeUiEvent.onScannedResult(scannedText))
            Log.d("HomeScreenLog", scannedText)
        } else {
            onEvent(HomeUiEvent.onScanCancelled)
        }
    }

    val height = LocalConfiguration.current.screenHeightDp.dp
    val width = LocalConfiguration.current.screenWidthDp.dp
    LaunchedEffect(Unit) {
        if (!PermissionManager.isAllPermissionGranted(context)) {
            permissionResultLauncher.launch(
                permissions
            )
        }
        uiEffect.collect {
            when (it) {

                HomeUiEffect.NavigateToMousepad -> onNavigateToMousepad()


                HomeUiEffect.NavigateToPcGuide -> {}
                HomeUiEffect.NavigateToOptions -> {}
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
            ) {
                Text(
                    text = "WELCOME BACK",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = lightGrey300,
                    modifier = Modifier
                        .align(Alignment.Start)
                )
                Text(
                    text = "Alen Roy",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.Start)
                )
            }
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(green500, Color(0xFF0094FF))
                        ),
                        shape = RoundedCornerShape(15.dp)
                    )
                    .border(BorderStroke(1.dp, darkBlue800), shape = RoundedCornerShape(15.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "A",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = darkBlue800,
                    modifier = Modifier
                        .padding(14.dp)
                )
            }

        }
        Spacer(Modifier.height(15.dp))
//        if (uiState.isFirstTime) {
//            BackgroundThemeCard() {
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                ) {
//                    FirstTimeUserHomeSection()
//
//                }
//            }
//            Spacer(Modifier.height(15.dp))
//            MyPrimaryButton(
//                text = "Scan QR Code to Begin"
//            ) {
//                onNavigateToScannerScreen()
//            }
//            Spacer(Modifier.height(15.dp))
//            MySecondaryButton(
//                "Download Taptrack PC app"
//            ) { }
//        } else {
        ConnectionCard(
            connectionStatus = uiState.connectionStatus,
            latency = uiState.latency?:0,
            onScanQrClicked = onNavigateToScannerScreen,
            onCancelClicked = {
            },
            onOpenTrackpadClicked = onNavigateToMousepad
        )
//        }

        if (uiState.connectionStatus is ConnectionStatus.Connecting) {
            Spacer(Modifier.height(16.dp))
            ConnectionStepProgressCard(
                currentStep = uiState.connectionStatus.step
            )
        }else if(uiState.connectionStatus is ConnectionStatus.Connected){
            Spacer(Modifier.height(12.dp))
            ConnectionDetailCard(
                deviceName = uiState.deviceName,
                networkName = uiState.networkName
            )
        }
    }


}


@Preview
@Composable
private fun HomeScreenPreview() {
    Scaffold(
        containerColor = darkBlue900
    ) {
        HomeScreen(
            uiState = HomeUiState(
                isFirstTime = true
            ),
            onEvent = {},
            uiEffect = MutableSharedFlow(),
            onNavigateToMousepad = {},
            onNavigateToScannerScreen = {},
            modifier = Modifier.padding(it)
        )
    }
}