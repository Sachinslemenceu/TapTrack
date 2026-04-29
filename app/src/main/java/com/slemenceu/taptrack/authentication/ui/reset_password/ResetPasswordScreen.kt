package com.slemenceu.taptrack.authentication.ui.reset_password

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slemenceu.taptrack.R
import com.slemenceu.taptrack.authentication.ui.login_screen.LoginUiEvent
import com.slemenceu.taptrack.authentication.ui.login_screen.LoginUiEvent.OnEmailChanged
import com.slemenceu.taptrack.authentication.ui.splash_screen.composable.MyButton
import com.slemenceu.taptrack.authentication.ui.splash_screen.composable.MyTextField
import com.slemenceu.taptrack.core.composables.AppTopBar
import com.slemenceu.taptrack.ui.theme.darkBlue800
import com.slemenceu.taptrack.ui.theme.darkBlue900
import com.slemenceu.taptrack.ui.theme.green500
import com.slemenceu.taptrack.ui.theme.lightGrey300
import com.slemenceu.taptrack.ui.theme.lightGrey400
import com.slemenceu.taptrack.ui.theme.lightGrey800
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun ResetPasswordScreen(
    uiState: ResetPasswordUiState,
    onEvent: (ResetPasswordUiEvent) -> Unit,
    uiEffect: SharedFlow<ResetPasswordUiEffect>,
    onNavigateToLoginScreen: () -> Unit,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val contactSupportText = buildAnnotatedString {
        append("Check your spam folder or \n")

        withLink(
            LinkAnnotation.Clickable(
                tag = "support",
                styles = TextLinkStyles(
                    style = SpanStyle(color = green500, fontWeight = FontWeight.Bold)
                ),
                linkInteractionListener = { }
            )
        ) {
            append("contact support")
        }
        append(" if email not received.")

    }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        uiEffect.collect { uiEffect ->
            when(uiEffect){
                is ResetPasswordUiEffect.OnResetLinkSent -> {
                    Toast.makeText(
                        context,
                        "Reset link sent to your email",
                        Toast.LENGTH_SHORT
                    ).show()
                    onNavigateToLoginScreen()
                }
                is ResetPasswordUiEffect.ShowToast -> {
                    Toast.makeText(
                        context,
                        uiEffect.message,
                        Toast.LENGTH_SHORT
                    ).show()
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
                text = "Reset Password",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Start)
            )
            Spacer(Modifier.height(9.dp))
            Text(
                text = "Enter your email and we'll send you a \nlink to reset your password.",
                fontSize = 12.sp,
                color = lightGrey400,
                modifier = Modifier
                    .align(Alignment.Start)
            )
            Spacer(Modifier.height(40.dp))
            MyTextField(
                value = uiState.email,
                onValueChange = { onEvent(ResetPasswordUiEvent.OnEmailChanged(it)) },
                placeholder = "you@example.com",
                leadingIcon = Icons.Outlined.Email

            )
            Spacer(Modifier.height(40.dp))
            MyButton(
                text = "Send Reset Link",
                onClick = {
                    onEvent(ResetPasswordUiEvent.OnSendResetLinkClicked)
                }
            )
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
                    text = "or",
                    fontSize = 11.sp,
                    color = lightGrey300,
                    modifier = Modifier
                        .padding(horizontal = 14.dp, vertical = 20.dp)
                )
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color(0xFF1E2330),
                    modifier = Modifier
                        .weight(1f)
                )
            }
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = lightGrey800
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = darkBlue800
                ),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = contactSupportText,
                    style = MaterialTheme.typography.bodySmall.copy(color = lightGrey300),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun ResetPasswordScreenPreview() {
    Scaffold(
        containerColor = darkBlue900
    ) {
        ResetPasswordScreen(
            uiState = ResetPasswordUiState(),
            onBackClicked = {},
            onNavigateToLoginScreen = {},
            onEvent = {},
            uiEffect = MutableSharedFlow(),
            modifier = Modifier.padding(it)
        )
    }
}