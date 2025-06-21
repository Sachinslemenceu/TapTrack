package com.slemenceu.taptrack.mousepad.ui.options_screen.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import com.slemenceu.taptrack.R

@Composable
fun OptionItem(
    iconColor: Color,
    @DrawableRes icon: Int,
    text: String,
    isPreference: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Card(
            shape = CircleShape,
            colors = CardDefaults.cardColors(
                containerColor = iconColor
            )
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = icon),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.padding(10.dp),
            )
        }
        Text(
            text = text,
            fontSize = 17.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(horizontal = 12.dp),
        )
        Spacer(Modifier.weight(1f))
        if (isPreference){
            Switch(
                checked = false,
                onCheckedChange = { onClick() }
            )
        } else {
            IconButton(
                onClick = {onClick()}
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Color(0xFFA6A6A6)
                )
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
private fun OptionItemPreview() {
    OptionItem(
        icon = R.drawable.dark_mode,
        iconColor = Color.Black,
        text = "Dark Mode",
        onClick = {},
        isPreference = false
    )
}