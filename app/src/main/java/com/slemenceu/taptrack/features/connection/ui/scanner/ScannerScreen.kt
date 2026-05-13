package com.slemenceu.taptrack.features.connection.ui.scanner

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slemenceu.taptrack.R
import com.slemenceu.taptrack.core.composables.AppTopBar
import com.slemenceu.taptrack.core.composables.BackgroundThemeCard
import com.slemenceu.taptrack.core.composables.MySecondaryButton
import com.slemenceu.taptrack.features.connection.ui.scanner.composables.CameraPreviewContent
import com.slemenceu.taptrack.ui.theme.darkBlue900
import com.slemenceu.taptrack.ui.theme.green500
import com.slemenceu.taptrack.ui.theme.lightGrey400

@Composable
fun ScannerScreen(
    onBackClicked: () -> Unit,
    onNavigateToManualConnection: () -> Unit,
    onNavigateToHomeScreen:() -> Unit,
    viewModel: ScannerViewModel,
    modifier: Modifier = Modifier
) {
    AppTopBar(
        onBackClicked = onBackClicked,
        title = buildAnnotatedString { append("Scan to Connect") }
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxWidth()
        ) {
            Spacer(Modifier.height(75.dp))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
            ) {
                CameraPreviewContent(
                    viewModel = viewModel,
                    onQrCodeScanned = {qrCode->
                        viewModel.connectToPcWithQrCode(
                            qrCode,
                            onResult = { success ->
                                if(success){
                                    Log.d("ScannerScreen", "Successfully connected to PC with QR code")
                                    onNavigateToHomeScreen()
                                } else {
                                    Log.e("ScannerScreen", "Failed to connect to PC with QR code")
                                    onNavigateToHomeScreen()
                                }
                            }
                        )
                    },
                    modifier = Modifier
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.scan_icon),
                        contentDescription = "Scan Icon",
                        tint = Color.Unspecified
                    )
                    Text(
                        text = "ALIGN QR CODE IN FRAME",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = green500,
                        modifier = Modifier
                            .padding(top = 16.dp)
                    )
                }

            }
            Spacer(Modifier.weight(0.5f))

            Text(
                text = "Point at TapTrack PC app",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Start)
            )
            Spacer(Modifier.height(9.dp))
            Text(
                text = "Open TapTrack on Windows or Mac. \nThe QR code appears on the main screen.",
                fontSize = 12.sp,
                color = lightGrey400,
                modifier = Modifier
                    .align(Alignment.Start)
            )
            Spacer(Modifier.height(9.dp))

            BackgroundThemeCard(
                backgroundColor = green500.copy(0.05f),
                borderColor = green500.copy(0.15f),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                ) {
                    Spacer(Modifier.weight(0.3f))
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.wifi_icon),
                        contentDescription = "Wifi Icon",
                        tint = green500,
                    )
                    Spacer(Modifier.weight(0.2f))
                    Column() {
                        Text(
                            text = "Home-WiFi-5G",
                            fontSize = 12.sp,
                            color = green500,
                        )
                        Text(
                            text = "Connected — same as PC required",
                            fontSize = 10.sp,
                            color = lightGrey400,
                        )
                    }

                    Spacer(Modifier.weight(1f))
                }
            }
            Spacer(Modifier.height(9.dp))
            MySecondaryButton(
                text = "Enter IP address manually",
                textColor = lightGrey400,
                onClick = onNavigateToManualConnection,
                modifier = Modifier

            )

            Spacer(Modifier.weight(1f))
        }
    }
}


@Preview
@Composable
private fun ScannerScreenPreview() {
//    Scaffold(
//        containerColor = darkBlue900
//    ) {
//        ScannerScreen(
//            modifier = Modifier
//                .padding(it)
//        )
//    }
}