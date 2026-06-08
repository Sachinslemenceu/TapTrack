package com.slemenceu.taptrack.features.mousepad.ui.settings_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slemenceu.taptrack.core.ui.composables.AppTopBar
import com.slemenceu.taptrack.core.ui.composables.MySecondaryButton
import com.slemenceu.taptrack.features.mousepad.ui.settings_screen.composable.SettingsMenuButton
import com.slemenceu.taptrack.ui.theme.red500

@Composable
fun SettingsScreen(
    onBackClicked: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToSecurity: () -> Unit,
    onNavigateToSupport: () -> Unit,
    onNavigateToAbout: () -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppTopBar(
        onBackClicked = onBackClicked,
        title = buildAnnotatedString { append("Settings") }
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxWidth()
        ) {
            Spacer(Modifier.height(75.dp))
            Text(
                text = "Account",
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Start)
            )
            Spacer(Modifier.height(14.dp))
            SettingsMenuButton(
                menuName = "Profile",
                onClick = onNavigateToProfile
            )
            Spacer(Modifier.height(8.dp))
            SettingsMenuButton(
                menuName = "Security",
                onClick = onNavigateToSecurity
            )
            Spacer(Modifier.height(14.dp))
            Text(
                text = "More",
                color = Color.White,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Start)
            )
            Spacer(Modifier.height(14.dp))
            SettingsMenuButton(
                menuName = "Customer Support",
                onClick = onNavigateToSupport
            )
            Spacer(Modifier.height(8.dp))
            SettingsMenuButton(
                menuName = "About",
                onClick = onNavigateToAbout
            )
            Spacer(Modifier.weight(1f))
            MySecondaryButton(
                text = "Logout",
                textColor = red500,
                containerColor = red500.copy(0.12f),
                borderColor = red500.copy(0.22f)
            ) { 
                onLogout()
            }
            Spacer(Modifier.weight(0.3f))
        }

    }
}


@Preview
@Composable
private fun SettingsScreenPreview() {
    SettingsScreen(
        onBackClicked = {},
        onNavigateToProfile = {},
        onNavigateToSecurity = {},
        onNavigateToSupport = {},
        onNavigateToAbout = {},
        onLogout = {}
    )
}
