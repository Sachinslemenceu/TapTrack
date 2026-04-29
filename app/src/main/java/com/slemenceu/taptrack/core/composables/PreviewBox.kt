package com.slemenceu.taptrack.core.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.slemenceu.taptrack.ui.theme.darkBlue900

@Composable
fun PreviewBox(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = { }
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .background(
                color = darkBlue900
            )
    ) {
        content()
    }
}