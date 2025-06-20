package com.slemenceu.taptrack.mousepad.ui.home_screen.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slemenceu.taptrack.ui.theme.lightGrey


@Composable
fun SectionHeading(
    title: String,
    subtext: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = title,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier,
    )
    Text(
        text = subtext,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        modifier = modifier
            .padding(vertical = 10.dp),
        color = lightGrey,
    )
}
