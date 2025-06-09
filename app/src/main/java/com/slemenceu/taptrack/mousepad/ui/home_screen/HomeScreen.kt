package com.slemenceu.taptrack.mousepad.ui.home_screen

import android.Manifest
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.slemenceu.taptrack.core.utils.PermissionManager
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
    navigateToLogin: () -> Unit
) {
    val context = LocalContext.current
    var showDialog = remember { mutableStateOf(false) }
    val permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_WIFI_STATE
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
                }
            }
        }
        Column(
           modifier = Modifier
                .padding(it)
                .fillMaxWidth()
        ) {
            WifiStatusCard(
                ssid = uiState.ssid,
                isConnected = uiState.isConnected,
                openWifiSettings = {
                    onEvent(HomeUiEvent.onOpenWifiSettings)
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
        uiEffect = MutableSharedFlow(),
        onEvent = {},
        uiState = HomeUiState(
            ssid = "SSID",
            isConnected = true,
        )
    )

}