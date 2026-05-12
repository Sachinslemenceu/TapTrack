package com.slemenceu.taptrack.mousepad.ui.home_screen

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
import com.slemenceu.taptrack.core.composables.MyPrimaryButton
import com.slemenceu.taptrack.core.composables.BackgroundThemeCard
import com.slemenceu.taptrack.core.composables.MySecondaryButton
import com.slemenceu.taptrack.core.utils.findActivity
import com.slemenceu.taptrack.mousepad.ui.home_screen.composables.FirstTimeUserHomeSection
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
    navigateToMousepad: () -> Unit,
    navigateToPcGuide: () -> Unit,
    navigateToOptions: () -> Unit
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
                onEvent(HomeUiEvent.loadInitialWifiInfo)
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
    val expandedHeight = height * 0.8f
    val peekHeight = height * 0.6f

//    val scaffoldState = rememberBottomSheetScaffoldState()
//    val isExpanded = scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded

//    val size by animateFloatAsState(
//        targetValue = if (isExpanded) 0f else 1f,
//        animationSpec = tween(
//            durationMillis = 500,
//            easing = LinearEasing,
//        ),
//    )
//    val move by animateFloatAsState(
//        targetValue = if (isExpanded) 0f else 100f,
//        animationSpec = tween(
//            durationMillis = 300,
//            easing = LinearEasing,
//        ),
//    )
//    val scale by animateFloatAsState(
//        targetValue = if (isExpanded) 1f else 0f,
//        animationSpec = tween(
//            durationMillis = 300,
//            easing = LinearEasing,
//        ),
//    )
//    val degree by animateFloatAsState(
//        targetValue = if (isExpanded) 360f else 0f,
//        animationSpec = tween(
//            durationMillis = 500,
//            easing = LinearEasing,
//        ),
//    )

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
            ) { }
            Spacer(Modifier.height(15.dp))
            MySecondaryButton(
                "Download Taptrack PC app"
            ) { }
        } else {

        }

    }
//
//    BottomSheetScaffold(
//        sheetContent = {
//            BottomSheetContent(
//                screenWidth = width,
//                expandedHeight = expandedHeight,
//                isConnected = uiState.mousepad.isConnected,
//                onNavigateToMousepad = {
//                    onEvent(HomeUiEvent.onNavigateToMousepad)
//                },
//                onConnectToMousepad = {
//                    activity?.let { onEvent(HomeUiEvent.onOpenScanner(it)) }
//                },
//                onPcGuideClicked = {
//                    onEvent(HomeUiEvent.onPcGuideClicked)
//                },
//                onOptionsClicked = {
//                    onEvent(HomeUiEvent.onOptionsClicked)
//                }
//            )
//        },
//        scaffoldState = scaffoldState,
//        sheetPeekHeight = peekHeight,
//        containerColor = violet40,
//        sheetContainerColor = Color.White,
//
//    ) {
//        LaunchedEffect(Unit) {
//            if(!PermissionManager.isAllPermissionGranted(context)) {
//                permissionResultLauncher.launch(
//                    permissions
//                )
//            }
//            onEvent(HomeUiEvent.startWifiTrackingEvent)
//            uiEffect.collect {
//                when (it) {
//
//                    HomeUiEffect.NavigateToMousepad -> navigateToMousepad()
//                    is HomeUiEffect.onQrScanClicked -> {
//                        launcher.launch(it.intent)
//                    }
//
//                    HomeUiEffect.onQrScanCancelled -> {
//                        Toast.makeText(context, "Scanning Cancelled", Toast.LENGTH_SHORT).show()
//                    }
//
//                    HomeUiEffect.NavigateToPcGuide -> navigateToPcGuide()
//                    HomeUiEffect.NavigateToOptions -> navigateToOptions()
//                }
//            }
//        }
//        Box(
//            modifier = modifier
//                .padding(it)
//                .fillMaxSize()
//        ) {
//            Image(
//                imageVector = ImageVector.vectorResource(R.drawable.cloudvector),
//                contentDescription = "Cloud Image",
//                modifier = Modifier
//                    .width(width)
//                    .height(height / 2)
//                    .padding(top = height / 18)
//                    .offset(x = width / 5)
//                    .align(Alignment.TopCenter),
//                contentScale = ContentScale.FillBounds,
//            )
//
//            Image(
//                imageVector = ImageVector.vectorResource(R.drawable.dot_icon),
//                contentDescription = "dot Icon",
//                modifier = Modifier
//                    .padding(top = height / 10)
//                    .offset(x = width / 7, y = move.dp)
//                    .scale(scale)
//                    .align(Alignment.TopCenter),
//                contentScale = ContentScale.FillBounds,
//            )
//            Image(
//                imageVector = ImageVector.vectorResource(R.drawable.duolink),
//                contentDescription = "Duo Link",
//                modifier = Modifier
//                    .align(Alignment.BottomEnd)
//                    .padding(end = 50.dp)
//                    .width(width / 2)
//                    .scale(size)
//                    .rotate(degree),
//                contentScale = ContentScale.FillBounds,
//            )
//            Card(
//                modifier = Modifier
//                    .padding(top = 50.dp, start = 30.dp),
//                colors = CardDefaults.cardColors(
//                    containerColor = violet30,
//                )
//            ) {
//                val wifiIcon = if (uiState.isConnected) R.drawable.wifi_icon else R.drawable.wifi_off_icon
//                IconButton(
//                    onClick = {
//                        onEvent(HomeUiEvent.onOpenWifiSettings)
//                    },
//                ) {
//                    Icon(
//                        imageVector = ImageVector.vectorResource(wifiIcon),
//                        contentDescription = "wifi connected icon",
//                        tint = if (uiState.isConnected) lightGreen else Color.Red,
//                        modifier = Modifier.size(40.dp)
//                    )
//                }
//            }
//        }
//    }

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
            navigateToMousepad = {},
            navigateToPcGuide = {},
            navigateToOptions = {},
            modifier = Modifier.padding(it)
        )
    }
}