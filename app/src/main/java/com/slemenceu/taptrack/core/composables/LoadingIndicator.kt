package com.slemenceu.taptrack.core.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.slemenceu.taptrack.ui.theme.green500

@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.7f)),
        contentAlignment = Alignment.Center,

        ) {
        CircularProgressIndicator(
            strokeWidth = 4.dp,
            color = green500
        )
    }
}


@Preview
@Composable
private fun LoadingIndicatorPreview() {
    LoadingIndicator()
}