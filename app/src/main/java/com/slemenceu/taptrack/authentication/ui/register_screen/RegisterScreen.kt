package com.slemenceu.taptrack.authentication.ui.register_screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slemenceu.taptrack.R
import com.slemenceu.taptrack.authentication.ui.login_screen.LoginUiEvent.OnEmailChanged
import com.slemenceu.taptrack.authentication.ui.login_screen.LoginUiEvent.OnPasswordChanged
import com.slemenceu.taptrack.authentication.ui.splash_screen.composable.MyButton
import com.slemenceu.taptrack.authentication.ui.splash_screen.composable.MyTextField
import com.slemenceu.taptrack.ui.theme.alegreya
import com.slemenceu.taptrack.ui.theme.darkViolet
import com.slemenceu.taptrack.ui.theme.lightGrey10
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    uiState: RegisterUiState,
    onEvent: (RegisterUiEvent) -> Unit,
    uiEffect: SharedFlow<RegisterUiEffect>,
    onNavigateToHome:() -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        uiEffect.collect {
            when (it) {
                RegisterUiEffect.NavigateToHome -> {
                    onNavigateToHome()
                }
                RegisterUiEffect.PasswordUnmatched -> {
                    Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    Scaffold(
        containerColor = lightGrey10
    ) {
        Column(
            modifier = modifier
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Spacer(Modifier.height(30.dp))
            Image(
                painter = painterResource(R.drawable.register_img),
                contentDescription = "Register Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 75.dp)
            )
            Spacer(Modifier.height(40.dp))
            Text(
                text = stringResource(R.string.register),
                fontSize = 16.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                color = darkViolet
            )
            Spacer(Modifier.height(40.dp))
            MyTextField(
                value = uiState.email,
                onValueChange = { onEvent(RegisterUiEvent.OnEmailChanged(it)) },
                placeholder = stringResource(R.string.email),
                containerColor = Color.White,
                contentColor = Color.Black,
                modifier = Modifier.padding(horizontal = 30.dp),
                trailingIcon = Icons.Outlined.Email

            )
            Spacer(Modifier.height(20.dp))
            MyTextField(
                value = uiState.password,
                onValueChange = { onEvent(RegisterUiEvent.OnPasswordChanged(it)) },
                placeholder = stringResource(R.string.password),
                containerColor = Color.White,
                contentColor = Color.Black,
                modifier = Modifier.padding(horizontal = 30.dp),
                trailingIcon = Icons.Outlined.Lock
            )
            Spacer(Modifier.height(20.dp))
            MyTextField(
                value = uiState.confirmPassword,
                onValueChange = { onEvent(RegisterUiEvent.OnConfirmPasswordChanged(it)) },
                placeholder = stringResource(R.string.confirm_password),
                containerColor = Color.White,
                contentColor = Color.Black,
                modifier = Modifier.padding(horizontal = 30.dp),
                trailingIcon = Icons.Outlined.Lock
            )
            Spacer(Modifier.height(20.dp))
            Text(
                text = stringResource(R.string.password_rules),
                fontSize = 14.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.ExtraLight,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 40.dp)
            )
            Spacer(Modifier.weight(1f))
            MyButton(
                text = stringResource(R.string.register_btn),
                modifier = Modifier.padding(horizontal = 40.dp),
                onClick = { onEvent(RegisterUiEvent.OnRegisterClicked) }
            )
            Spacer(Modifier.weight(1f))

        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(
        onNavigateToHome = {},
        uiState = RegisterUiState(
            email = "Sachin",
            password = "password",
            confirmPassword = "confirmPassword"
        ),
        onEvent = {},
        uiEffect = MutableSharedFlow(),
    )
}