package com.slemenceu.taptrack.mousepad.ui.home_screen.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.slemenceu.taptrack.R
import com.slemenceu.taptrack.ui.theme.darkGrey
import com.slemenceu.taptrack.ui.theme.darkViolet
import com.slemenceu.taptrack.ui.theme.overTheRainbow
import com.slemenceu.taptrack.ui.theme.violet10

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    onLogoutClicked: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Tap Track",
                fontFamily = overTheRainbow,
            )
        },
        actions = {
            IconButton(
                onClick = { onLogoutClicked()}
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ExitToApp,
                    contentDescription = "Logout",
                    tint = darkGrey
                )
            }
        },
        navigationIcon = {
            IconButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu icon",
                    tint = darkGrey
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.White
        )
    )

}

@Preview
@Composable
fun TopAppBarPreview() {
    Scaffold(
        topBar = {
            TopAppBar(
                onLogoutClicked = {}
            )
        }
    ) {
        Column(modifier = Modifier.padding(it)) {

        }
    }
}