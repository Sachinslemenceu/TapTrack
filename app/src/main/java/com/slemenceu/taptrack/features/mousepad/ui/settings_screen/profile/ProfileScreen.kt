package com.slemenceu.taptrack.features.mousepad.ui.settings_screen.profile

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
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
fun ProfileScreen(
    uiState: ProfileUiState,
    onEvent: (ProfileUiEvent) -> Unit,
    uiEffect: SharedFlow<ProfileUiEffect>,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        uiEffect.collect { effect ->
            when (effect) {
                is ProfileUiEffect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
                ProfileUiEffect.NavigateBack -> {
                    onBackClicked()
                }
            }
        }
    }

    AppTopBar(
        onBackClicked = onBackClicked,
        title = buildAnnotatedString { append("Profile") }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(Modifier.height(75.dp))
            
            Text(
                text = "PERSONAL INFORMATION",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = green500
            )
            Text(
                text = "Update your profile",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(Modifier.height(30.dp))

            MyTextField(
                value = uiState.name,
                onValueChange = { onEvent(ProfileUiEvent.OnNameChanged(it)) },
                placeholder = "Full Name",
                leadingIcon = Icons.Outlined.Person
            )

            Spacer(Modifier.height(16.dp))

            MyTextField(
                value = uiState.email,
                onValueChange = { onEvent(ProfileUiEvent.OnEmailChanged(it)) },
                placeholder = "Email Address",
                leadingIcon = Icons.Outlined.Email
            )

            Spacer(Modifier.height(24.dp))

            MyPrimaryButton(
                text = "Update Profile",
                onClick = { onEvent(ProfileUiEvent.OnUpdateProfileClicked) }
            )
        }
    }

    if (uiState.isLoading) {
        LoadingIndicator()
    }
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    Scaffold(containerColor = darkBlue900) {
        ProfileScreen(
            uiState = ProfileUiState(
                name = "John Doe",
                email = "john@example.com"
            ),
            onEvent = {},
            uiEffect = MutableSharedFlow(),
            onBackClicked = {},
            modifier = Modifier.padding(it)
        )
    }
}
