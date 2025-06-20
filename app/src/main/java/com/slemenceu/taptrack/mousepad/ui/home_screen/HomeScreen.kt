package com.slemenceu.taptrack.mousepad.ui.home_screen

import android.Manifest
import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.zxing.integration.android.IntentIntegrator
import com.slemenceu.taptrack.R
import com.slemenceu.taptrack.core.utils.PermissionManager
import com.slemenceu.taptrack.mousepad.ui.home_screen.composables.BottomSheetContent
import com.slemenceu.taptrack.ui.theme.lightGreen
import com.slemenceu.taptrack.ui.theme.violet30
import com.slemenceu.taptrack.ui.theme.violet40
import kotlinx.coroutines.flow.SharedFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
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

    val height = LocalConfiguration.current.screenHeightDp.dp
    val width = LocalConfiguration.current.screenWidthDp.dp
    val expandedHeight = height * 0.8f
    val peekHeight = height * 0.6f

    val scaffoldState = rememberBottomSheetScaffoldState()
    val isExpanded = scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded

    val size by animateFloatAsState(
        targetValue = if (isExpanded) 0f else 1f,
        animationSpec = tween(
            durationMillis = 500,
            easing = LinearEasing,
        ),
    )
    val move by animateFloatAsState(
        targetValue = if (isExpanded) 0f else 100f,
        animationSpec = tween(
            durationMillis = 300,
            easing = LinearEasing,
        ),
    )
    val scale by animateFloatAsState(
        targetValue = if (isExpanded) 1f else 0f,
        animationSpec = tween(
            durationMillis = 300,
            easing = LinearEasing,
        ),
    )
    val degree by animateFloatAsState(
        targetValue = if (isExpanded) 360f else 0f,
        animationSpec = tween(
            durationMillis = 500,
            easing = LinearEasing,
        ),
    )

    BottomSheetScaffold(
        sheetContent = {
            BottomSheetContent(
                screenWidth = width,
                expandedHeight = expandedHeight,
                isConnected = uiState.mousepad.isConnected,
                onNavigateToMousepad = {
                    onEvent(HomeUiEvent.onNavigateToMousepad)
                },
                onConnectToMousepad = {
                    onEvent(HomeUiEvent.onOpenScanner(activity))
                }
            )
        },
        scaffoldState = scaffoldState,
        sheetPeekHeight = peekHeight,
        containerColor = violet40,
        sheetContainerColor = Color.White,
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
        Box(
            modifier = modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.cloudvector),
                contentDescription = "Cloud Image",
                modifier = Modifier
                    .width(width)
                    .height(height / 2)
                    .padding(top = height / 18)
                    .offset(x = width / 5)
                    .align(Alignment.TopCenter),
                contentScale = ContentScale.FillBounds,
            )

            Image(
                imageVector = ImageVector.vectorResource(R.drawable.dot_icon),
                contentDescription = "dot Icon",
                modifier = Modifier
                    .padding(top = height / 10)
                    .offset(x = width / 7, y = move.dp)
                    .scale(scale)
                    .align(Alignment.TopCenter),
                contentScale = ContentScale.FillBounds,
            )
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.duolink),
                contentDescription = "Duo Link",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 50.dp)
                    .width(width / 2)
                    .scale(size)
                    .rotate(degree),
                contentScale = ContentScale.FillBounds,
            )
            Card(
                modifier = Modifier
                    .padding(top = 50.dp, start = 30.dp),
                colors = CardDefaults.cardColors(
                    containerColor = violet30,
                )
            ) {
                val wifiIcon = if (uiState.isConnected) R.drawable.wifi_icon else R.drawable.wifi_off_icon
                IconButton(
                    onClick = {
                        onEvent(HomeUiEvent.onOpenWifiSettings)
                    },
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(wifiIcon),
                        contentDescription = "wifi connected icon",
                        tint = if (uiState.isConnected) lightGreen else Color.Red,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }
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