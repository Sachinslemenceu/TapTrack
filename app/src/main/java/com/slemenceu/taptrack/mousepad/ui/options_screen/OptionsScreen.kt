package com.slemenceu.taptrack.mousepad.ui.options_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slemenceu.taptrack.R
import com.slemenceu.taptrack.mousepad.ui.options_screen.composables.OptionItem
import com.slemenceu.taptrack.mousepad.ui.pc_guide_screen.composables.TopAppBar
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect

@Composable
fun OptionsScreen(
    onEvent: (OptionsUiEvent) -> Unit,
    uiEffect: SharedFlow<OptionsUiEffect>,
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit,
) {

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        uiEffect.collect {effect ->
            when(effect) {
                OptionsUiEffect.NavigateToHome -> onNavigateToHome()
                OptionsUiEffect.NavigateToLogin -> onNavigateToLogin()
                OptionsUiEffect.ShowToast -> {
                    Toast.makeText(context, "This feature is not available yet", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = "Options",
                onBackClicked = {
                    onEvent(OptionsUiEvent.OnBackClicked)
                }
            )
        },
        containerColor = Color.White
    ) {
        var showDialog = remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
                .verticalScroll(
                    state = rememberScrollState()
                )
        ) {
            Heading("Preferences")
            OptionItem(
                icon = R.drawable.dark_mode,
                iconColor = Color.Black,
                text = "Dark Mode",
                isPreference = true,
                onClick = {
                    onEvent(OptionsUiEvent.OnUnfinishedFeatureClicked)
                }
            )
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            OptionItem(
                icon = R.drawable.scroll,
                iconColor = Color(0xFFEAEF4E),
                text = "Scroll Enabled",
                isPreference = true,
                onClick = {
                    onEvent(OptionsUiEvent.OnUnfinishedFeatureClicked)
                }
            )
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            OptionItem(
                icon = R.drawable.keyboard,
                iconColor = Color(0xFF7370C5),
                text = "Keyboard Enabled",
                isPreference = true,
                onClick = {
                    onEvent(OptionsUiEvent.OnUnfinishedFeatureClicked)
                }
            )
            Heading("Account")
            OptionItem(
                icon = R.drawable.user_name,
                iconColor = Color(0xFFFE294D),
                text = "Username",
                isPreference = false,
                onClick = {
                    onEvent(OptionsUiEvent.OnUnfinishedFeatureClicked)
                }
            )
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            OptionItem(
                icon = R.drawable.delete,
                iconColor = Color(0xFF0084FE),
                text = "Delete Account",
                isPreference = false,
                onClick = {
                    onEvent(OptionsUiEvent.OnUnfinishedFeatureClicked)
                }
            )
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            OptionItem(
                icon = R.drawable.logout,
                iconColor = Color(0xFF5AD439),
                text = "Logout",
                isPreference = false,
                onClick = {
                    showDialog.value = true
                }
            )
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
                            onEvent(OptionsUiEvent.OnLogoutClicked)
                            showDialog.value = false
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

@Composable
fun Heading(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontSize = 13.sp,
        fontWeight = FontWeight.Normal,
        color = Color(0xFFA6A6A6),
        modifier = modifier.padding(20.dp)
    )
}
@Preview
@Composable
private fun OptionsScreenPreview() {
    OptionsScreen(
        onNavigateToLogin = {},
        onEvent = {},
        uiEffect = MutableSharedFlow(),
        onNavigateToHome = {}

    )
}