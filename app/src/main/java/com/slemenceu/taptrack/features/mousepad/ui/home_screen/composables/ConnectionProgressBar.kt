package com.slemenceu.taptrack.features.mousepad.ui.home_screen.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.slemenceu.taptrack.R
import com.slemenceu.taptrack.ui.theme.blue500
import com.slemenceu.taptrack.ui.theme.darkBlue800
import com.slemenceu.taptrack.ui.theme.green500

@Composable
fun ConnectionProgressBar(
    modifier: Modifier = Modifier,
    progress: Float = 0.5f
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        // Outer progress bar (Green, 2.dp width)
        CircularProgressIndicator(
            progress = { progress },
            modifier = Modifier.size(130.dp),
            color = blue500,
            strokeWidth = 3.dp,
            strokeCap = StrokeCap.Round,
            trackColor = darkBlue800
        )

        // Inner progress bar (Red, 1.dp width) with padding
        CircularProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .size(110.dp)
                .padding(10.dp),
            color = green500,
            strokeWidth = 2.dp,
            strokeCap = StrokeCap.Round,
            trackColor = darkBlue800
        )

        // Icon in center
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.monitor_icon),
            contentDescription = "Monitor Icon",
            tint = Color.Unspecified,
            modifier = Modifier.size(60.dp)
        )
    }
}

@Preview
@Composable
private fun ConnectionProgressBarPreview() {
    ConnectionProgressBar()
}