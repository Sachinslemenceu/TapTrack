package com.slemenceu.taptrack.features.mousepad.ui.home_screen

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slemenceu.taptrack.core.ui.composables.BackgroundThemeCard
import com.slemenceu.taptrack.core.ui.composables.MyPrimaryButton
import com.slemenceu.taptrack.core.ui.composables.MySecondaryButton
import com.slemenceu.taptrack.core.utils.PermissionManager
import com.slemenceu.taptrack.core.utils.findActivity
import com.slemenceu.taptrack.features.connection.domain.models.ConnectionStatus
import com.slemenceu.taptrack.features.mousepad.ui.home_screen.composables.ConnectionCard
import com.slemenceu.taptrack.features.mousepad.ui.home_screen.composables.ConnectionDetailCard
import com.slemenceu.taptrack.features.mousepad.ui.home_screen.composables.ConnectionStepProgressCard
import com.slemenceu.taptrack.features.mousepad.ui.home_screen.composables.FirstTimeUserHomeSection
import com.slemenceu.taptrack.ui.theme.darkBlue800
import com.slemenceu.taptrack.ui.theme.darkBlue900
import com.slemenceu.taptrack.ui.theme.lightGrey300
import com.slemenceu.taptrack.ui.theme.lightGrey400
import com.slemenceu.taptrack.ui.theme.lightGrey800

//@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    onEvent: (HomeUiEvent) -> Unit,
    onNavigateToScannerScreen: () -> Unit,
    onNavigateToMousepad: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    3
    val context = LocalContext.current
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
            onEvent(HomeUiEvent.OnPermissionResult(result))
            if (result.all { it.value }) {
            }
        }
    )

    LaunchedEffect(Unit) {
        if (!PermissionManager.isAllPermissionGranted(context)) {
            permissionResultLauncher.launch(
                permissions
            )
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
                    text = uiState.userName,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.Start)
                )
            }
            Surface(
                onClick = onNavigateToSettings,
                shape = RoundedCornerShape(15.dp),
                border = BorderStroke(1.dp, darkBlue800),
                modifier = Modifier.size(48.dp),
                color = lightGrey800
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Back",
                    tint = lightGrey400,
                    modifier = Modifier
                        .padding(14.dp)
                )
            }

        }
        Spacer(Modifier.height(15.dp))
        if (uiState.isFirstTime) {
            BackgroundThemeCard() {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    FirstTimeUserHomeSection()

                }
            }
            Spacer(Modifier.height(15.dp))
            MyPrimaryButton(
                text = "Scan QR Code to Begin"
            ) {
                onNavigateToScannerScreen()
            }
            Spacer(Modifier.height(15.dp))
            MySecondaryButton(
                "Download Taptrack PC app"
            ) {

            }
        } else {
            ConnectionCard(
                connectionStatus = uiState.connectionStatus,
                latency = uiState.latency ?: 0,
                onScanQrClicked = onNavigateToScannerScreen,
                onCancelClicked = {
                },
                onOpenTrackpadClicked = onNavigateToMousepad
            )
        }

        if (uiState.connectionStatus is ConnectionStatus.Connecting) {
            Spacer(Modifier.height(16.dp))
            ConnectionStepProgressCard(
                currentStep = uiState.connectionStatus.step
            )
        } else if (uiState.connectionStatus is ConnectionStatus.Connected) {
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
            onNavigateToMousepad = {},
            onNavigateToScannerScreen = {},
            onNavigateToSettings = {},
            modifier = Modifier.padding(it)
        )
    }
}