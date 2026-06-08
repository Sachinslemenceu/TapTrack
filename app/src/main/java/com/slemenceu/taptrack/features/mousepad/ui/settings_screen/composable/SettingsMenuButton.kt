package com.slemenceu.taptrack.features.mousepad.ui.settings_screen.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slemenceu.taptrack.core.ui.composables.BackgroundThemeCard
import com.slemenceu.taptrack.ui.theme.blue500
import com.slemenceu.taptrack.ui.theme.darkBlue800
import com.slemenceu.taptrack.ui.theme.green500
import com.slemenceu.taptrack.ui.theme.lightGrey300
import com.slemenceu.taptrack.ui.theme.lightGrey800

@Composable
fun SettingsMenuButton(
    menuName: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        color = lightGrey800,
        border = BorderStroke(1.dp, darkBlue800),
        shape = RoundedCornerShape(15.dp),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(17.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = menuName,
                fontSize = 13.sp,
                color = lightGrey300
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Right Arrow",
                tint = green500
            )

        }
    }
}


@Preview
@Composable
private fun SettingsMenuButtonPreview() {
    SettingsMenuButton(
        "Profile", {}
    )
}