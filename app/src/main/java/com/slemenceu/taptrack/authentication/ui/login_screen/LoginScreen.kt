package com.slemenceu.taptrack.authentication.ui.login_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slemenceu.taptrack.R
import com.slemenceu.taptrack.ui.theme.overTheRainbow
import com.slemenceu.taptrack.ui.theme.violet10

@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    Scaffold(
        containerColor = violet10
    ) {
        Column(
            modifier.padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(40.dp))
            Image(
                painter = painterResource(R.drawable.top_table_img),
                contentDescription = "Top table image",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp,)
            )
            Spacer(Modifier.height(15.dp))
            Text(
                text = "One Step Away",
                fontSize = 25.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = overTheRainbow,
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview(modifier: Modifier = Modifier) {
    LoginScreen()
}