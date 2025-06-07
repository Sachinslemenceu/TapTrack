package com.slemenceu.taptrack.authentication.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.slemenceu.taptrack.ui.theme.alegreya
import com.slemenceu.taptrack.ui.theme.overTheRainbow

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Scaffold {
        Column(modifier.padding(it)) {
            Text("Home Screen",
                fontFamily = overTheRainbow,
                fontWeight = FontWeight.Normal,
                fontSize = 25.sp
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()

}