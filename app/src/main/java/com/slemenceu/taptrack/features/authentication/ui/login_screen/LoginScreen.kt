package com.slemenceu.taptrack.features.authentication.ui.login_screen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slemenceu.taptrack.R
import com.slemenceu.taptrack.core.ui.composables.MyPrimaryButton
import com.slemenceu.taptrack.features.authentication.ui.splash_screen.composable.MyTextField
import com.slemenceu.taptrack.core.ui.composables.AppTopBar
import com.slemenceu.taptrack.core.ui.composables.LoadingIndicator
import com.slemenceu.taptrack.ui.theme.darkBlue800
import com.slemenceu.taptrack.ui.theme.darkBlue900
import com.slemenceu.taptrack.ui.theme.green500
import com.slemenceu.taptrack.ui.theme.lightGrey300
import com.slemenceu.taptrack.ui.theme.lightGrey800
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    uiState: LoginUiState,
    onEvent: (LoginUiEvent) -> Unit,
    uiEffect: SharedFlow<LoginUiEffect>,
    onBackClicked: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
) {
    val context = LocalContext.current
    val createAccountText =buildAnnotatedString {
        append("Don't have an account? ")

        withLink(
            LinkAnnotation.Clickable(
                tag = "register",
                styles = TextLinkStyles(
                    style = SpanStyle(color = green500, fontWeight = FontWeight.Bold)
                ),
                linkInteractionListener = { onNavigateToRegister() }
            )
        ) {
            append("Create one")
        }

    }
    val TAG = "LoginScreen"
    LaunchedEffect(Unit) {
        uiEffect.collect {
            when (it) {
                is LoginUiEffect.NavigateToHome -> {
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()

                    onNavigateToHome()
                }

                is LoginUiEffect.InvalidCredential -> {
                    Toast.makeText(context, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
    AppTopBar(
        onBackClicked = onBackClicked
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(75.dp))
            Text(
                text ="WELCOME BACK",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = green500,
                modifier = Modifier
                    .align(Alignment.Start)
            )
            Text(
                text ="Sign in to your \naccount",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Start)
            )
            Spacer(Modifier.height(40.dp))
            MyTextField(
                value = uiState.email,
                onValueChange = { onEvent(LoginUiEvent.OnEmailChanged(it)) },
                placeholder = stringResource(R.string.email),
                leadingIcon = Icons.Outlined.Email

            )
            Spacer(Modifier.height(14.dp))
            MyTextField(
                value = uiState.password,
                onValueChange = { onEvent(LoginUiEvent.OnPasswordChanged(it)) },
                placeholder = stringResource(R.string.password),
                title = "Password",
                leadingIcon = Icons.Outlined.Lock,
            )
            Spacer(Modifier.height(14.dp))
            TextButton(
                onClick = onNavigateToForgotPassword,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    text = "Forgot Password?",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Normal,
                    color = green500,
                )
            }
            Spacer(Modifier.height(40.dp))
            MyPrimaryButton(
                text = "Sign In",
                onClick = { onEvent(LoginUiEvent.OnLoginClicked) }
            )
            Spacer(Modifier.height(40.dp))
            Spacer(Modifier.weight(1f))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color(0xFF1E2330),
                    modifier = Modifier
                        .weight(1f)
                )
                Text(
                    text = "Or continue with",
                    fontSize = 11.sp,
                    color = lightGrey300,
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                )
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color(0xFF1E2330),
                    modifier = Modifier
                        .weight(1f)
                )
            }
            Spacer(Modifier.height(10.dp))
            OutlinedButton(
                onClick = {
                    // Handle Google Sign-In
                    Toast.makeText(context, "Google Sign-In is Currently not available", Toast.LENGTH_SHORT).show()
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = lightGrey800
                ),
                border = BorderStroke(
                    1.dp,
                    darkBlue800
                ),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Spacer(Modifier.weight(0.3f))
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.google_icon),
                        contentDescription = "Google Icon",
                        tint = Color.Unspecified,
                    )
                    Spacer(Modifier.weight(0.5f))
                    Text(
                        text = "Continue with Google",
                        fontSize = 13.sp,
                        color = Color.White,
                    )
                    Spacer(Modifier.weight(1f))
                }
            }
            Text(
                text = createAccountText,
                style = MaterialTheme.typography.bodySmall.copy(color = lightGrey300)
            )

            Spacer(Modifier.weight(1f))
        }
    }

    if (uiState.isLoading) {
        LoadingIndicator()
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    Scaffold(
        containerColor = darkBlue900
    ) {
        LoginScreen(
            uiState = LoginUiState(
                email = "Sachin",
                password = "password",
                isLoading = false
            ),
            onEvent = {},
            uiEffect = MutableSharedFlow(),
            onBackClicked = {},
            onNavigateToHome = {},
            onNavigateToRegister = {},
            onNavigateToForgotPassword = {},
            modifier = Modifier.padding(it)
        )
    }
}