package com.slemenceu.taptrack.authentication.ui.login_screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants.IterateForever
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.slemenceu.taptrack.R
import com.slemenceu.taptrack.authentication.ui.login_screen.LoginUiEvent.OnEmailChanged
import com.slemenceu.taptrack.authentication.ui.login_screen.LoginUiEvent.OnPasswordChanged
import com.slemenceu.taptrack.authentication.ui.splash_screen.SplashUiEffect
import com.slemenceu.taptrack.authentication.ui.splash_screen.composable.MyButton
import com.slemenceu.taptrack.authentication.ui.splash_screen.composable.MyTextField
import com.slemenceu.taptrack.mousepad.ui.home_screen.HomeUiEvent
import com.slemenceu.taptrack.mousepad.ui.home_screen.HomeUiState
import com.slemenceu.taptrack.ui.theme.alegreya
import com.slemenceu.taptrack.ui.theme.overTheRainbow
import com.slemenceu.taptrack.ui.theme.violet10
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    uiState: LoginUiState,
    onEvent: (LoginUiEvent) -> Unit,
    uiEffect: SharedFlow<LoginUiEffect>,
    onNavigateToHome:() -> Unit,
    onNavigateToRegister:() -> Unit
) {
    val context = LocalContext.current
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loadinng_ani))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = IterateForever,
        speed = 1f,
    )
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

               is LoginUiEffect.NavigateToRegister -> {
                   Log.d(TAG,"Navigate to register")
                   onNavigateToRegister()
               }
            }
        }
    }
    Scaffold(
        containerColor = violet10
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(40.dp))
                Image(
                    painter = painterResource(R.drawable.top_table_img),
                    contentDescription = "Top table image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 60.dp)
                )
                Spacer(Modifier.height(15.dp))
                Text(
                    text = "One Step Away",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = overTheRainbow,
                    color = Color.White
                )
                Spacer(Modifier.height(40.dp))
                MyTextField(
                    value = uiState.email,
                    onValueChange = { onEvent(OnEmailChanged(it)) },
                    placeholder = stringResource(R.string.email),
                    containerColor = Color.White,
                    contentColor = Color.Black,
                    modifier = Modifier.padding(horizontal = 30.dp),
                    trailingIcon = Icons.Outlined.Email

                )
                Spacer(Modifier.height(20.dp))
                MyTextField(
                    value = uiState.password,
                    onValueChange = { onEvent(OnPasswordChanged(it)) },
                    placeholder = stringResource(R.string.password),
                    containerColor = Color.White,
                    contentColor = Color.Black,
                    modifier = Modifier.padding(horizontal = 30.dp),
                    trailingIcon = Icons.Outlined.Lock
                )
                Spacer(Modifier.height(40.dp))
                MyButton(
                    text = stringResource(R.string.login),
                    modifier = Modifier.padding(horizontal = 40.dp),
                    onClick = { onEvent(LoginUiEvent.OnLoginClicked) }
                )
                Spacer(Modifier.height(40.dp))
                Row {
                    Text(
                        text = stringResource(
                            R.string.register_here,
                        ),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = alegreya,
                        color = Color.White,
                        modifier = Modifier.clickable(
                            onClick = {
                                Log.d(TAG,"Register button clicked")
                                onEvent(LoginUiEvent.OnRegisterClicked)
                            }
                        )

                        )
                    Spacer(Modifier.width(5.dp))
                    Image(
                        painter = painterResource(R.drawable.click_img),
                        contentDescription = "Click image",
                        modifier = Modifier.size(34.dp)
                    )

                }
                Spacer(Modifier.weight(1f))
                Text(
                    text = stringResource(
                        R.string.app_version,
                    ),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = alegreya,
                    color = Color.White,

                    )
                Spacer(Modifier.weight(1f))
            }
        }
        if(uiState.isLoading){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center,

            ) {
                LottieAnimation(
                    composition = composition,
                    progress = { progress }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        uiState = LoginUiState(
            email = "Sachin",
            password = "password",
            isLoading = false
        ),
        onEvent = {},
        uiEffect = MutableSharedFlow(),
        onNavigateToHome = {},
        onNavigateToRegister = {}
    )
}