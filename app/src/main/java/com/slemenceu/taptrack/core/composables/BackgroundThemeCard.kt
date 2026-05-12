package com.slemenceu.taptrack.core.composables

import androidx.annotation.ColorLong
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.slemenceu.taptrack.ui.theme.darkBlue800
import com.slemenceu.taptrack.ui.theme.lightGrey800

@Composable
fun BackgroundThemeCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = lightGrey800,
    borderColor: Color = darkBlue800,
    content: @Composable () -> Unit,
) {
    Surface(
        color = backgroundColor,
        border = BorderStroke(1.dp, borderColor),
        shape = RoundedCornerShape(15.dp),
        modifier = modifier
            .fillMaxWidth()
    ) {
        content()
    }
}
