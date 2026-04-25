package com.slemenceu.taptrack.authentication.ui.splash_screen.composable

import android.provider.CalendarContract
import android.provider.CalendarContract.Colors
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slemenceu.taptrack.ui.theme.darkViolet
import com.slemenceu.taptrack.ui.theme.green500

@Composable
fun MyButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = green500
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            text = text,
            fontSize = 15.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
            )
    }
}

@Preview(showBackground = true)
@Composable
fun MyButtonPreview() {
    MyButton(text = "Hello"){

    }
    
}