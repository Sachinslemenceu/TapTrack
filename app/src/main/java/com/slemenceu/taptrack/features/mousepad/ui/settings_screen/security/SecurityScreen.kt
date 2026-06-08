package com.slemenceu.taptrack.features.mousepad.ui.settings_screen.security

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slemenceu.taptrack.core.ui.composables.AppTopBar
import com.slemenceu.taptrack.core.ui.composables.LoadingIndicator
import com.slemenceu.taptrack.core.ui.composables.MyPrimaryButton
import com.slemenceu.taptrack.features.authentication.ui.splash_screen.composable.MyTextField
import com.slemenceu.taptrack.ui.theme.darkBlue900
import com.slemenceu.taptrack.ui.theme.green500
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun SecurityScreen(
    uiState: SecurityUiState,
    onEvent: (SecurityUiEvent) -> Unit,
    uiEffect: SharedFlow<SecurityUiEffect>,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        uiEffect.collect { effect ->
            when (effect) {
                is SecurityUiEffect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
                SecurityUiEffect.NavigateBack -> {
                    onBackClicked()
                }
            }
        }
    }

    AppTopBar(
        onBackClicked = onBackClicked,
        title = buildAnnotatedString { append("Security") }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(Modifier.height(75.dp))
            
            Text(
                text = "PASSWORD SETTINGS",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = green500
            )
            Text(
                text = "Change your password",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(Modifier.height(30.dp))

            MyTextField(
                value = uiState.newPassword,
                onValueChange = { onEvent(SecurityUiEvent.OnNewPasswordChanged(it)) },
                placeholder = "New Password",
                leadingIcon = Icons.Outlined.Lock,
                title = "New Password"
            )

            Spacer(Modifier.height(16.dp))

            MyTextField(
                value = uiState.confirmPassword,
                onValueChange = { onEvent(SecurityUiEvent.OnConfirmPasswordChanged(it)) },
                placeholder = "Confirm Password",
                leadingIcon = Icons.Outlined.Lock,
                title = "Confirm Password"
            )

            Spacer(Modifier.height(24.dp))

            MyPrimaryButton(
                text = "Update Password",
                onClick = { onEvent(SecurityUiEvent.OnUpdatePasswordClicked) }
            )

            if (uiState.errorMessage != null) {
                Text(
                    text = uiState.errorMessage,
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }

    if (uiState.isLoading) {
        LoadingIndicator()
    }
}

@Preview
@Composable
private fun SecurityScreenPreview() {
    Scaffold(containerColor = darkBlue900) {
        SecurityScreen(
            uiState = SecurityUiState(),
            onEvent = {},
            uiEffect = MutableSharedFlow(),
            onBackClicked = {},
            modifier = Modifier.padding(it)
        )
    }
}
