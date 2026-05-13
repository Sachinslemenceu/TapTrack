package com.slemenceu.taptrack.features.authentication.ui.register_screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slemenceu.taptrack.core.composables.MyPrimaryButton
import com.slemenceu.taptrack.features.authentication.ui.splash_screen.composable.MyTextField
import com.slemenceu.taptrack.core.composables.AppTopBar
import com.slemenceu.taptrack.ui.theme.darkBlue900
import com.slemenceu.taptrack.ui.theme.green500
import com.slemenceu.taptrack.ui.theme.lightGrey300
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    uiState: RegisterUiState,
    onEvent: (RegisterUiEvent) -> Unit,
    uiEffect: SharedFlow<RegisterUiEffect>,
    onNavigateToHome: () -> Unit,
    onNavigateToLogin:() -> Unit,
    onBackClicked: () -> Unit
) {
    val context = LocalContext.current

    val termsAndPrivacyText = buildAnnotatedString {
        append("By signing up you agree to our ")

        withLink(
            LinkAnnotation.Clickable(
                tag = "terms",
                styles = TextLinkStyles(
                    style = SpanStyle(color = green500, fontWeight = FontWeight.Bold)
                ),
                linkInteractionListener = { /* open terms */ }
            )
        ) {
            append("Terms of Service")
        }

        append(" and ")

        withLink(
            LinkAnnotation.Clickable(
                tag = "privacy",
                styles = TextLinkStyles(
                    style = SpanStyle(color = green500, fontWeight = FontWeight.Bold)
                ),
                linkInteractionListener = { /* open privacy */ }
            )
        ) {
            append("Privacy Policy")
        }
    }
    val signInText =buildAnnotatedString {
        append("Already have an account? ")

        withLink(
            LinkAnnotation.Clickable(
                tag = "signIn",
                styles = TextLinkStyles(
                    style = SpanStyle(color = green500, fontWeight = FontWeight.Bold)
                ),
                linkInteractionListener = { onNavigateToLogin() }
            )
        ) {
            append("Sign In")
        }

    }



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

    AppTopBar(
        onBackClicked = onBackClicked
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(75.dp))
            Text(
                text = "CREATE ACCOUNT",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = green500,
                modifier = Modifier
                    .align(Alignment.Start)
            )
            Text(
                text = "Get started \nfor free",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Start)
            )
            Spacer(Modifier.height(40.dp))
            MyTextField(
                value = uiState.name,
                onValueChange = { onEvent(RegisterUiEvent.OnNameChanged(it)) },
                placeholder = "Enter your name",
                title = "Full name",
                leadingIcon = Icons.Outlined.Person
            )
            Spacer(Modifier.height(14.dp))
            MyTextField(
                value = uiState.email,
                onValueChange = { onEvent(RegisterUiEvent.OnEmailChanged(it)) },
                placeholder = "you@example.com",
                leadingIcon = Icons.Outlined.Email

            )
            Spacer(Modifier.height(20.dp))
            MyTextField(
                value = uiState.password,
                onValueChange = { onEvent(RegisterUiEvent.OnPasswordChanged(it)) },
                placeholder = "Min. 8 characters",
                title = "Password",
                leadingIcon = Icons.Outlined.Lock
            )

            Spacer(Modifier.weight(1f))
            Text(
                text = termsAndPrivacyText,
                style = MaterialTheme.typography.bodySmall.copy(color = lightGrey300)
            )
            Spacer(Modifier.height(10.dp))

            MyPrimaryButton(
                text = "Create Account",
                onClick = { onEvent(RegisterUiEvent.OnRegisterClicked) }
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = signInText,
                style = MaterialTheme.typography.bodySmall.copy(color = lightGrey300)
            )

            Spacer(Modifier.weight(1f))

        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    Scaffold(
        containerColor = darkBlue900
    ) {
        RegisterScreen(
            onNavigateToHome = {},
            onNavigateToLogin = {},
            onBackClicked = {},
            uiState = RegisterUiState(
            ),
            onEvent = {},
            uiEffect = MutableSharedFlow(),
            modifier = Modifier.padding(it)
        )
    }
}