package com.slemenceu.taptrack.mousepad.ui.home_screen

import android.Manifest
import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.zxing.integration.android.IntentIntegrator
import com.slemenceu.taptrack.core.utils.PermissionManager
import com.slemenceu.taptrack.mousepad.ui.home_screen.composables.MousepadSection
import com.slemenceu.taptrack.mousepad.ui.home_screen.composables.OtpDialog
import com.slemenceu.taptrack.mousepad.ui.home_screen.composables.TopAppBar
import com.slemenceu.taptrack.mousepad.ui.home_screen.composables.WifiStatusCard
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onEvent: (HomeUiEvent) -> Unit,
    uiEffect: SharedFlow<HomeUiEffect>,
    navigateToLogin: () -> Unit,
    navigateToMousepad: () -> Unit
) {
    val context = LocalContext.current
    val activity = context as Activity
    var showDialog = remember { mutableStateOf(false) }
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
                onEvent(HomeUiEvent.loadInitialWifiInfo)
            }
        }
    )

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {result ->
        val intentResult = IntentIntegrator.parseActivityResult(result.resultCode, result.data)
        val scannedText = intentResult?.contents
        if (scannedText != null){
            onEvent(HomeUiEvent.onScannedResult(scannedText))
        } else {
            onEvent(HomeUiEvent.onScanCancelled)
        }
    }

    Scaffold(
        topBar = {TopAppBar(
            onLogoutClicked = {
                showDialog.value = true
            }
        )},
        containerColor = Color.White
    ) {
        LaunchedEffect(Unit) {
            if(!PermissionManager.isAllPermissionGranted(context)) {
                permissionResultLauncher.launch(
                    permissions
                )
            }
            onEvent(HomeUiEvent.startWifiTrackingEvent)
            uiEffect.collect {
                when (it) {
                    is HomeUiEffect.NavigateToLogin -> {
                        Toast.makeText(context, "Successfully nLogged out", Toast.LENGTH_SHORT).show()
                        navigateToLogin()
                    }

                    HomeUiEffect.NavigateToMousepad -> navigateToMousepad()
                    is HomeUiEffect.onQrScanClicked -> {
                        launcher.launch(it.intent)
                    }

                    HomeUiEffect.onQrScanCancelled -> {
                        Toast.makeText(context, "Scanning Cancelled", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        Column(
           modifier = Modifier
                .padding(it)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WifiStatusCard(
                ssid = uiState.ssid,
                isConnected = uiState.isConnected,
                openWifiSettings = {
                    onEvent(HomeUiEvent.onOpenWifiSettings)
                }
                )
            HorizontalDivider(modifier = Modifier.padding(vertical = 15.dp,horizontal = 20.dp))
            MousepadSection(
                isConnected = uiState.mousepad.isConnected,
                onNavigateToMousepad = {
                    onEvent(HomeUiEvent.onNavigateToMousepad)
                },
                onConnectToMousepad = {
                    onEvent(HomeUiEvent.onOpenScanner(activity))
                }
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 15.dp,horizontal = 20.dp))
        }
        if (showDialog.value) {
            AlertDialog(
                    onDismissRequest = {
                        showDialog.value = false
                    },
                    title = { Text("Logout") },
                    text = { Text("Are you sure you want to logout?") },
                    confirmButton = {
                        OutlinedButton(
                            onClick = {
                                showDialog.value = false
                                onEvent(HomeUiEvent.onLogOutClicked)
                            }
                        ) {
                            Text("Yes",
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {

    HomeScreen(
        navigateToLogin = {},
        navigateToMousepad = {},
        uiEffect = MutableSharedFlow(),
        onEvent = {},
        uiState = HomeUiState(
            ssid = "SSID",
            isConnected = false,
        )
    )

}