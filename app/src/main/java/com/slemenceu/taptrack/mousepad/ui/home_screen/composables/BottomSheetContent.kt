package com.slemenceu.taptrack.mousepad.ui.home_screen.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.slemenceu.taptrack.R
import com.slemenceu.taptrack.ui.theme.lightBlue
import com.slemenceu.taptrack.ui.theme.violet20


@Composable
fun BottomSheetContent(
    modifier: Modifier = Modifier,
    screenWidth: Dp,
    expandedHeight: Dp,
    isConnected: Boolean,
    onNavigateToMousepad: () -> Unit,
    onConnectToMousepad: () -> Unit
){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(expandedHeight)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SectionHeading(
            title = "Hi Sachin",
            subtext = "Welcome, Let's swipe",
            modifier = Modifier.align(Alignment.Start)
        )
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
        ) {
            OptionCard(
                backgroundColor = violet20,
                text = "How to connect " +
                        "to PC ?",
                icon = R.drawable.pc_icon,
                modifier = Modifier.weight(1f)
            )
            OptionCard(
                backgroundColor = lightBlue,
                text = "Options",
                icon = R.drawable.settings_icon,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(Modifier.height(10.dp))
        SectionHeading(
            title = "Your Connection",
            subtext = "Oops not yet connected",
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(Modifier.weight(1f))
        ConnectionSection(
            isConnected = isConnected,
            screenWidth = screenWidth,
            onNavigateToMousepad = onNavigateToMousepad,
            onConnectToMousepad = onConnectToMousepad
        )
        Spacer(Modifier.weight(1f))

    }
}
