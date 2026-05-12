package com.slemenceu.taptrack.core.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slemenceu.taptrack.ui.theme.green500

@Composable
fun MyPrimaryButton(
    modifier: Modifier = Modifier.Companion,
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = green500
        ),
        shape = RoundedCornerShape(10.dp)
    ) {

        Text(
            text = text,
            fontSize = 15.sp,
            color = Color.Companion.Black,
            fontWeight = FontWeight.Companion.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyPrimaryButtonPreview() {
    MyPrimaryButton(text = "Hello") {

    }

}